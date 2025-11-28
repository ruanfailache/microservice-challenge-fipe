package com.fipe.infrastructure.adapter.out.cache;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fipe.domain.model.Brand;
import com.fipe.domain.model.Vehicle;
import com.fipe.domain.port.out.cache.VehicleCachePort;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.value.ValueCommands;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class RedisCacheAdapter implements VehicleCachePort {
    
    private static final Logger LOG = Logger.getLogger(RedisCacheAdapter.class);
    private static final String BRANDS_CACHE_KEY = "brands:all";
    private static final String VEHICLES_BY_BRAND_PREFIX = "vehicles:brand:";
    private static final Duration CACHE_TTL = Duration.ofMinutes(30);
    
    @Inject
    RedisDataSource redisDataSource;
    
    private final ObjectMapper objectMapper;
    private final ValueCommands<String, String> valueCommands;
    
    public RedisCacheAdapter() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.valueCommands = null; // Will be initialized after injection
    }
    
    private ValueCommands<String, String> getValueCommands() {
        if (valueCommands != null) {
            return valueCommands;
        }
        return redisDataSource.value(String.class, String.class);
    }
    
    @Override
    public Optional<List<Brand>> getAllBrands() {
        try {
            String cached = getValueCommands().get(BRANDS_CACHE_KEY);
            if (cached != null) {
                LOG.info("Cache hit for all brands");
                List<Brand> brands = objectMapper.readValue(cached, new TypeReference<List<Brand>>() {});
                return Optional.of(brands);
            }
            LOG.info("Cache miss for all brands");
            return Optional.empty();
        } catch (Exception e) {
            LOG.warnf("Error reading brands from cache: %s", e.getMessage());
            return Optional.empty();
        }
    }
    
    @Override
    public void cacheAllBrands(List<Brand> brands) {
        try {
            String json = objectMapper.writeValueAsString(brands);
            getValueCommands().setex(BRANDS_CACHE_KEY, CACHE_TTL.getSeconds(), json);
            LOG.infof("Cached %d brands", brands.size());
        } catch (Exception e) {
            LOG.warnf("Error caching brands: %s", e.getMessage());
        }
    }
    
    @Override
    public Optional<List<Vehicle>> getVehiclesByBrandCode(String brandCode) {
        try {
            String key = VEHICLES_BY_BRAND_PREFIX + brandCode;
            String cached = getValueCommands().get(key);
            if (cached != null) {
                LOG.infof("Cache hit for vehicles of brand: %s", brandCode);
                List<Vehicle> vehicles = objectMapper.readValue(cached, new TypeReference<List<Vehicle>>() {});
                return Optional.of(vehicles);
            }
            LOG.infof("Cache miss for vehicles of brand: %s", brandCode);
            return Optional.empty();
        } catch (Exception e) {
            LOG.warnf("Error reading vehicles from cache for brand %s: %s", brandCode, e.getMessage());
            return Optional.empty();
        }
    }
    
    @Override
    public void cacheVehiclesByBrandCode(String brandCode, List<Vehicle> vehicles) {
        try {
            String key = VEHICLES_BY_BRAND_PREFIX + brandCode;
            String json = objectMapper.writeValueAsString(vehicles);
            getValueCommands().setex(key, CACHE_TTL.getSeconds(), json);
            LOG.infof("Cached %d vehicles for brand: %s", vehicles.size(), brandCode);
        } catch (Exception e) {
            LOG.warnf("Error caching vehicles for brand %s: %s", brandCode, e.getMessage());
        }
    }
    
    @Override
    public void invalidateVehicleCache(String brandCode) {
        try {
            String key = VEHICLES_BY_BRAND_PREFIX + brandCode;
            getValueCommands().getdel(key);
            LOG.infof("Invalidated cache for brand: %s", brandCode);
        } catch (Exception e) {
            LOG.warnf("Error invalidating cache for brand %s: %s", brandCode, e.getMessage());
        }
    }
    
    @Override
    public void invalidateAllCaches() {
        try {
            getValueCommands().getdel(BRANDS_CACHE_KEY);
            LOG.info("Invalidated all brands cache");
        } catch (Exception e) {
            LOG.warnf("Error invalidating all caches: %s", e.getMessage());
        }
    }
}
