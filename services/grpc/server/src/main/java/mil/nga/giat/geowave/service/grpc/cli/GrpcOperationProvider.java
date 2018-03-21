package mil.nga.giat.geowave.service.grpc.cli;

import mil.nga.giat.geowave.core.cli.spi.CLIOperationProviderSpi;

public class GrpcOperationProvider implements
		CLIOperationProviderSpi
{
	private static final Class<?>[] OPERATIONS = new Class<?>[] {
		RunGrpcCommand.class
	};

	@Override
	public Class<?>[] getOperations() {
		return OPERATIONS;
	}
}
