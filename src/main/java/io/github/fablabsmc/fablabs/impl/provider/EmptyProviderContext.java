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

package io.github.fablabsmc.fablabs.impl.provider;

import java.util.NoSuchElementException;

import io.github.fablabsmc.fablabs.api.provider.v1.ProviderContext;

public final class EmptyProviderContext implements ProviderContext {
	public static final EmptyProviderContext INSTANCE = new EmptyProviderContext();

	private EmptyProviderContext() {
	}

	@Override
	public <V> ProviderContext with(Key<V> key, V value) {
		// Return an instance backed by a map and then `with`
		return new MapBackedContextImpl().with(key, value);
	}

	@Override
	public <V> V get(Key<V> key) {
		return null;
	}
}
