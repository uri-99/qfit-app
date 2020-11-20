package com.example.qfit_app.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.example.qfit_app.api.ApiResponse;
import com.example.qfit_app.api.ApiService;
import com.example.qfit_app.api.classes.PagedList;
import com.example.qfit_app.api.classes.SportDTO;
import com.example.qfit_app.db.MyDatabase;
import com.example.qfit_app.db.entity.SportEntity;
import com.example.qfit_app.domain.Sport;
import com.example.qfit_app.vo.AbsentLiveData;
import com.example.qfit_app.vo.Resource;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.util.stream.Collectors.toList;

public class SportRepository {

    private static final String RATE_LIMITER_ALL_KEY = "@@all@@";

    private AppExecutors executors;
    private ApiService service;
    private MyDatabase database;
    private RateLimiter<String> rateLimit = new RateLimiter<>(10, TimeUnit.MINUTES);

    public AppExecutors getExecutors() {
        return executors;
    }

    public SportRepository(AppExecutors executors, ApiService service, MyDatabase database) {
        this.executors = executors;
        this.service = service;
        this.database = database;
    }

    private Sport mapSportEntityToDomain(SportEntity entity) {
        return new Sport(entity.id, entity.name, entity.detail);
    }

    private SportEntity mapSportDTOToEntity(SportDTO model) {
        return new SportEntity(model.getId(), model.getName(), model.getDetail());
    }

    private Sport mapSportDTOToDomain(SportDTO model) {
        return new Sport(model.getId(), model.getName(), model.getDetail());
    }

    private SportDTO mapSportDomainToModel(Sport domain) {
        SportDTO model = new SportDTO();
        model.setId(domain.getId());
        model.setName(domain.getName());
        model.setDetail(domain.getDetail());
        return model;
    }

    public LiveData<Resource<List<Sport>>> getSports() {

        return new NetworkBoundResource<List<Sport>, List<SportEntity>, PagedList<SportDTO>>(executors,
                entities -> {
                    return entities.stream()
                            .map(sportEntity -> new Sport(sportEntity.id, sportEntity.name, sportEntity.detail))
                            .collect(toList());
                },
                model -> {
                    return model.getResults().stream()
                            .map(SportDTO -> new SportEntity(SportDTO.getId(), SportDTO.getName(), SportDTO.getDetail()))
                            .collect(toList());
                },
                model -> {
                    return model.getResults().stream()
                            .map(SportDTO -> new Sport(SportDTO.getId(), SportDTO.getName(), SportDTO.getDetail()))
                            .collect(toList());
                }) {
            @Override
            protected void saveCallResult(@NonNull List<SportEntity> entities) {
                database.sportDao().deleteAll();
                database.sportDao().insert(entities);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<SportEntity> entities) {
                return ((entities == null) || (entities.size() == 0) || rateLimit.shouldFetch(RATE_LIMITER_ALL_KEY));
            }

            @Override
            protected boolean shouldPersist(@Nullable PagedList<SportDTO> model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<SportEntity>> loadFromDb() {
                return database.sportDao().findAll();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<SportDTO>>> createCall() {
                return service.getSports();
            }
        }.asLiveData();
    }

    public LiveData<Resource<List<Sport>>> getSports(int page, int size) {

        return new NetworkBoundResource<List<Sport>, List<SportEntity>, PagedList<SportDTO>>(executors,
                entities -> {
                    return entities.stream()
                            .map(sportEntity -> new Sport(sportEntity.id, sportEntity.name, sportEntity.detail))
                            .collect(toList());
                },
                model -> {
                    return model.getResults().stream()
                            .map(SportDTO -> new SportEntity(SportDTO.getId(), SportDTO.getName(), SportDTO.getDetail()))
                            .collect(toList());
                },
                model -> {
                    return model.getResults().stream()
                            .map(SportDTO -> new Sport(SportDTO.getId(), SportDTO.getName(), SportDTO.getDetail()))
                            .collect(toList());
                }) {
            @Override
            protected void saveCallResult(@NonNull List<SportEntity> entities) {
                database.sportDao().delete(size, page * size);
                database.sportDao().insert(entities);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<SportEntity> entities) {
                return ((entities == null) || (entities.size() == 0));
            }

            @Override
            protected boolean shouldPersist(@Nullable PagedList<SportDTO> model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<SportEntity>> loadFromDb() {
                return database.sportDao().findAll(size, page * size);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<SportDTO>>> createCall() {
                return service.getSports(page, size);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Sport>> getSport(int sportId) {
        return new NetworkBoundResource<Sport, SportEntity, SportDTO>(executors, this::mapSportEntityToDomain, this::mapSportDTOToEntity, this::mapSportDTOToDomain) {

            @Override
            protected void saveCallResult(@NonNull SportEntity entity) {
                database.sportDao().insert(entity);
            }

            @Override
            protected boolean shouldFetch(@Nullable SportEntity entity) {
                return (entity == null);
            }

            @Override
            protected boolean shouldPersist(@Nullable SportDTO model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<SportEntity> loadFromDb() {
                return database.sportDao().findById(sportId);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<SportDTO>> createCall() {
                return service.getSport(sportId);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Sport>> addSport(Sport sport) {

        return new NetworkBoundResource<Sport, SportEntity, SportDTO>(executors, this::mapSportEntityToDomain, this::mapSportDTOToEntity, this::mapSportDTOToDomain) {
            int sportId = 0;

            @Override
            protected void saveCallResult(@NonNull SportEntity entity) {
                sportId = entity.id;
                database.sportDao().insert(entity);
            }

            @Override
            protected boolean shouldFetch(@Nullable SportEntity entity) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable SportDTO model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<SportEntity> loadFromDb() {
                if (sportId == 0)
                    return AbsentLiveData.create();
                else
                    return database.sportDao().findById(sportId);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<SportDTO>> createCall() {
                SportDTO model = mapSportDomainToModel(sport);
                return service.addSport(model);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Sport>> modifySport(Sport sport) {
        return new NetworkBoundResource<Sport, SportEntity, SportDTO>(executors, this::mapSportEntityToDomain, this::mapSportDTOToEntity, this::mapSportDTOToDomain) {

            @Override
            protected void saveCallResult(@NonNull SportEntity entity) {
                database.sportDao().update(entity);
            }

            @Override
            protected boolean shouldFetch(@Nullable SportEntity entity) {
                return (entity != null);
            }

            @Override
            protected boolean shouldPersist(@Nullable SportDTO model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<SportEntity> loadFromDb() {
                return database.sportDao().findById(sport.getId());
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<SportDTO>> createCall() {
                SportDTO model = mapSportDomainToModel(sport);
                return service.modifySport(model.getId(), model);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> deleteSport(Sport sport) {
        return new NetworkBoundResource<Void, SportEntity, Void>(executors,
                entity -> {
                    return null;
                },
                model -> {
                    return null;
                },
                model -> {
                    return null;
                }) {

            @Override
            protected void saveCallResult(@NonNull SportEntity entity) {
                database.sportDao().delete(sport.getId());
            }

            @Override
            protected boolean shouldFetch(@Nullable SportEntity entity) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable Void model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<SportEntity> loadFromDb() {
                return database.sportDao().findById(sport.getId());
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return service.deleteSport(sport.getId());
            }
        }.asLiveData();
    }
}
