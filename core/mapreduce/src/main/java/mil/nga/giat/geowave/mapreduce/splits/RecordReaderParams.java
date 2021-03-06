package mil.nga.giat.geowave.mapreduce.splits;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import mil.nga.giat.geowave.core.index.ByteArrayId;
import mil.nga.giat.geowave.core.store.adapter.AdapterStore;
import mil.nga.giat.geowave.core.store.adapter.DataAdapter;
import mil.nga.giat.geowave.core.store.entities.GeoWaveRowIteratorTransformer;
import mil.nga.giat.geowave.core.store.index.PrimaryIndex;
import mil.nga.giat.geowave.core.store.operations.BaseReaderParams;
import mil.nga.giat.geowave.core.store.query.aggregate.Aggregation;

public class RecordReaderParams<T> extends
		BaseReaderParams<T>
{
	private final GeoWaveRowRange rowRange;

	public RecordReaderParams(
			final PrimaryIndex index,
			final AdapterStore adapterStore,
			final List<ByteArrayId> adapterIds,
			final double[] maxResolutionSubsamplingPerDimension,
			final Pair<DataAdapter<?>, Aggregation<?, ?, ?>> aggregation,
			final Pair<List<String>, DataAdapter<?>> fieldSubsets,
			final boolean isMixedVisibility,
			final GeoWaveRowRange rowRange,
			final Integer limit,
			final Integer maxRangeDecomposition,
			final GeoWaveRowIteratorTransformer<T> rowTransformer,
			final String... additionalAuthorizations ) {
		super(
				index,
				adapterStore,
				adapterIds,
				maxResolutionSubsamplingPerDimension,
				aggregation,
				fieldSubsets,
				isMixedVisibility,
				limit,
				maxRangeDecomposition,
				rowTransformer,
				additionalAuthorizations);
		this.rowRange = rowRange;
	}

	public GeoWaveRowRange getRowRange() {
		return rowRange;
	}
}
