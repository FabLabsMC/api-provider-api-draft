/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.fablabsmc.fablabs.api.provider.v1;

import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Unique reference to an API.
 */
public final class ApiKey<T> {
    private static final Map<Class<?>, Map<Identifier, ApiKey<?>>> apiKeys = new HashMap<>();

    private ApiKey() { }

    public static <T> ApiKey<T> create(Class<T> clazz, Identifier identifier) {
        Objects.requireNonNull(clazz, "encountered null class in ApiKey creation");
        Objects.requireNonNull(identifier, "encountered null identifier in ApiKey creation");

        apiKeys.putIfAbsent(clazz, new HashMap<>());
        apiKeys.get(clazz).putIfAbsent(identifier, new ApiKey<>());
        return (ApiKey<T>) apiKeys.get(clazz).get(identifier);
    }
}
