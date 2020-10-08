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

import net.minecraft.util.math.Direction;

/**
 * An enumeration of common access context keys.
 */
public final class AccessContextKeys {
	/**
	 * A provider context key which allows specification of the direction when providing an api.
	 */
	public static final ProviderContext.Key<Direction> DIRECTION = ProviderContext.key(Direction.class);
}
