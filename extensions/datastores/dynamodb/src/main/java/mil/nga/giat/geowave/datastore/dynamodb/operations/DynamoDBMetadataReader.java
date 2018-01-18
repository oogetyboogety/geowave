package mil.nga.giat.geowave.datastore.dynamodb.operations;

import java.nio.ByteBuffer;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.google.common.collect.Iterators;

import mil.nga.giat.geowave.core.store.CloseableIterator;
import mil.nga.giat.geowave.core.store.CloseableIteratorWrapper;
import mil.nga.giat.geowave.core.store.entities.GeoWaveMetadata;
import mil.nga.giat.geowave.core.store.metadata.AbstractGeoWavePersistence;
import mil.nga.giat.geowave.core.store.operations.MetadataQuery;
import mil.nga.giat.geowave.core.store.operations.MetadataReader;

public class DynamoDBMetadataReader implements
		MetadataReader
{
	private final static Logger LOGGER = LoggerFactory.getLogger(
			DynamoDBMetadataReader.class);
	private final DynamoDBOperations operations;

	public DynamoDBMetadataReader(
			final DynamoDBOperations operations ) {
		this.operations = operations;
	}

	@Override
	public CloseableIterator<GeoWaveMetadata> query(
			MetadataQuery query ) {
		String tableName = operations.getQualifiedTableName(
				AbstractGeoWavePersistence.METADATA_TABLE);

		if (query.hasPrimaryId()) {
			final QueryRequest queryRequest = new QueryRequest(
					tableName);

			if (query.hasSecondaryId()) {
				queryRequest.addQueryFilterEntry(
						DynamoDBOperations.METADATA_SECONDARY_ID_KEY,
						new Condition()
								.withAttributeValueList(
										new AttributeValue().withB(
												ByteBuffer.wrap(
														query.getSecondaryId())))
								.withComparisonOperator(
										ComparisonOperator.EQ));
			}
			queryRequest.addKeyConditionsEntry(
					DynamoDBOperations.METADATA_PRIMARY_ID_KEY,
					new Condition()
							.withAttributeValueList(
									new AttributeValue().withB(
											ByteBuffer.wrap(
													query.getPrimaryId())))
							.withComparisonOperator(
									ComparisonOperator.EQ));

			final QueryResult queryResult = operations.getClient().query(
					queryRequest);

			return new CloseableIteratorWrapper<>(
					null,
					Iterators.transform(
							queryResult.getItems().iterator(),
							new com.google.common.base.Function<Map<String, AttributeValue>, GeoWaveMetadata>() {
								@Override
								public GeoWaveMetadata apply(
										final Map<String, AttributeValue> result ) {

									return new GeoWaveMetadata(
											getPrimaryId(
													result),
											getSecondaryId(
													result),
											null,
											getValue(
													result));
								}
							}));

		}

		final ScanRequest scan = new ScanRequest(
				tableName);
		if (query.hasSecondaryId()) {
			scan.addScanFilterEntry(
					DynamoDBOperations.METADATA_SECONDARY_ID_KEY,
					new Condition()
							.withAttributeValueList(
									new AttributeValue().withB(
											ByteBuffer.wrap(
													query.getSecondaryId())))
							.withComparisonOperator(
									ComparisonOperator.EQ));
		}
		final ScanResult scanResult = operations.getClient().scan(
				scan);

		return new CloseableIteratorWrapper<>(
				null,
				Iterators.transform(
						scanResult.getItems().iterator(),
						new com.google.common.base.Function<Map<String, AttributeValue>, GeoWaveMetadata>() {
							@Override
							public GeoWaveMetadata apply(
									final Map<String, AttributeValue> result ) {

								return new GeoWaveMetadata(
										getPrimaryId(
												result),
										getSecondaryId(
												result),
										null,
										getValue(
												result));
							}
						}));
	}

	protected byte[] getPrimaryId(
			final Map<String, AttributeValue> map ) {
		final AttributeValue v = map.get(
				DynamoDBOperations.METADATA_PRIMARY_ID_KEY);
		if (v != null) {
			return v.getB().array();
		}
		return null;
	}

	protected byte[] getSecondaryId(
			final Map<String, AttributeValue> map ) {
		final AttributeValue v = map.get(
				DynamoDBOperations.METADATA_SECONDARY_ID_KEY);
		if (v != null) {
			return v.getB().array();
		}
		return null;
	}

	protected byte[] getValue(
			final Map<String, AttributeValue> map ) {
		final AttributeValue v = map.get(
				DynamoDBOperations.METADATA_VALUE_KEY);
		if (v != null) {
			return v.getB().array();
		}
		return null;
	}
}