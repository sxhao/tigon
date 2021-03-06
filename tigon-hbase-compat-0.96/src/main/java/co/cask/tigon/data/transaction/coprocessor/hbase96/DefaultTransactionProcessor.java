/*
 * Copyright © 2014 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package co.cask.tigon.data.transaction.coprocessor.hbase96;

import co.cask.tephra.Transaction;
import co.cask.tephra.coprocessor.TransactionStateCache;
import co.cask.tephra.hbase96.coprocessor.TransactionProcessor;
import co.cask.tephra.hbase96.coprocessor.TransactionVisibilityFilter;
import co.cask.tigon.data.increment.hbase96.IncrementFilter;
import co.cask.tigon.data.transaction.coprocessor.DefaultTransactionStateCacheSupplier;
import com.google.common.base.Supplier;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;
import org.apache.hadoop.hbase.filter.Filter;

/**
 * Implementation of the {@link TransactionProcessor}
 * coprocessor that uses {@link co.cask.tigon.data.transaction.coprocessor.DefaultTransactionStateCache}
 * to automatically refresh transaction state.
 */
public class DefaultTransactionProcessor extends TransactionProcessor {
  @Override
  protected Supplier<TransactionStateCache> getTransactionStateCacheSupplier(RegionCoprocessorEnvironment env) {
    String tableName = env.getRegion().getTableDesc().getNameAsString();
    String[] parts = tableName.split("\\.", 2);
    String tableNamespace = "";
    if (parts.length > 0) {
      tableNamespace = parts[0];
    }
    return new DefaultTransactionStateCacheSupplier(tableNamespace, env.getConfiguration());
  }

  @Override
  protected Filter getTransactionFilter(Transaction tx) {
    return new TransactionVisibilityFilter(tx, ttlByFamily, allowEmptyValues, new IncrementFilter());
  }
}
