/*
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
package io.trino.plugin.deltalake;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Collections.unmodifiableList;
import static java.util.Objects.requireNonNull;

public class DeltaLakeMergeResult
{
    private final List<String> partitionValues;
    private final Optional<String> oldFile;
    private final Optional<DataFileInfo> newFile;

    @JsonCreator
    public DeltaLakeMergeResult(List<String> partitionValues, Optional<String> oldFile, Optional<DataFileInfo> newFile)
    {
        // Immutable list does not allow nulls
        // noinspection Java9CollectionFactory
        this.partitionValues = unmodifiableList(new ArrayList<>(requireNonNull(partitionValues, "partitionValues is null")));
        this.oldFile = requireNonNull(oldFile, "oldFile is null");
        this.newFile = requireNonNull(newFile, "newFile is null");
        checkArgument(oldFile.isPresent() || newFile.isPresent(), "old or new must be present");
    }

    @JsonProperty
    public List<String> getPartitionValues()
    {
        return partitionValues;
    }

    @JsonProperty
    public Optional<String> getOldFile()
    {
        return oldFile;
    }

    @JsonProperty
    public Optional<DataFileInfo> getNewFile()
    {
        return newFile;
    }
}
