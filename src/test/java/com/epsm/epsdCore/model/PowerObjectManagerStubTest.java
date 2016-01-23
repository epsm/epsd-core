package com.epsm.epsdCore.model;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.epsm.epsmCore.model.consumption.ConsumerParametersStub;
import com.epsm.epsmCore.model.consumption.ConsumptionPermissionStub;
import com.epsm.epsmCore.model.dispatch.Parameters;
import com.epsm.epsmCore.model.generalModel.TimeService;
import com.epsm.epsmCore.model.generation.PowerStationGenerationSchedule;
import com.epsm.epsmCore.model.generation.PowerStationParameters;

public class PowerObjectManagerStubTest {
	private TimeService timeService;
	private DispatcherImpl dispatcher;
	private PowerObjectManagerStub manager;
	private PowerStationParameters powerStationParameters;
	private ConsumerParametersStub consumerParameters;
	private Parameters unknownParameters;
	private final int POWER_OBJECT_ID = 468;
	
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Before
	public void setUp(){
		timeService = new TimeService();
		dispatcher = mock(DispatcherImpl.class);
		manager = spy(new PowerObjectManagerStub(timeService));
		powerStationParameters = new PowerStationParameters(POWER_OBJECT_ID, LocalDateTime.MIN,
				LocalDateTime.MIN, 2);
		consumerParameters = new ConsumerParametersStub(POWER_OBJECT_ID, LocalDateTime.MIN,
				LocalDateTime.MIN);
		unknownParameters = new UnknownParameters(POWER_OBJECT_ID, LocalDateTime.MIN,
				LocalDateTime.MIN);
	}
	
	private class UnknownParameters extends Parameters{
		public UnknownParameters(long powerObjectId, LocalDateTime realTimeStamp,
				LocalDateTime simulationTimeStamp) {
			
			super(powerObjectId, realTimeStamp, simulationTimeStamp);
		}

		@Override
		public String toString() {
			return null;
		}
	}
	
	@Test
	public void exceptionInConstructorIfTimeServiceIsNull(){
		expectedEx.expect(IllegalArgumentException.class);
	    expectedEx.expectMessage("PowerObjectManagerStub constructor: timeService can't be null.");
	
	    new PowerObjectManagerStub(null);
	}
	
	
	@Test
	public void registersPowerStation(){
		boolean powerStationRegistered 
				= manager.registerObjectIfItTypeIsKnown(powerStationParameters);
		
		Assert.assertTrue(powerStationRegistered);
	}
	
	@Test
	public void registersConsumer(){
		boolean consumerRegistered 
				= manager.registerObjectIfItTypeIsKnown(consumerParameters);
		
		Assert.assertTrue(consumerRegistered);
	}
	
	
	
	
	@Test
	public void managerSendsConsumptionPermissionToKnownConsumers(){
		manager.registerObjectIfItTypeIsKnown(consumerParameters);
		manager.sendMessage(POWER_OBJECT_ID);
		
		verify(dispatcher).sendCommand(isA(ConsumptionPermissionStub.class));
	}
	
	@Test
	public void managerSendsNothingToUnknownPowerObject(){
		manager.registerObjectIfItTypeIsKnown(unknownParameters);
		manager.sendMessage(POWER_OBJECT_ID);
		
		verify(dispatcher, never()).sendCommand(any());
	}
}
