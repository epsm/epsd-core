package com.epsm.epsdCore.model;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epsm.epsmCore.model.bothConsumptionAndGeneration.LoadCurve;
import com.epsm.epsmCore.model.generalModel.TimeService;
import com.epsm.epsmCore.model.generation.GeneratorGenerationSchedule;
import com.epsm.epsmCore.model.generation.PowerStationGenerationSchedule;

//It is just stub. More complex model see com.epsm.electricPowerSystemModel.model.*;
public class PowerStationGenerationScheduleCalculatorStub {
	private TimeService timeService;
	private final static float[] PRECALCULATED_GENERATION_BY_HOURS = new float[]{
			55.15f,  50.61f,  47.36f,  44.11f, 	41.20f,  41.52f,
			40.87f,  48.66f,  64.89f,  77.86f,  85.00f,  84.34f,
			77.86f,  77.86f,  77.53f,  77.20f,  77.20f,  77.20f,
			77.20f,  77.20f,  77.20f,  77.20f,  77.20f,  77.20f 
	};
	private final int GENERATORS_QUANTITY = 2;
	private final int FIRST_GENERATOR_NUMBER = 1;
	private final int SECOND_GENERATOR_NUMBER = 2;
	private final boolean GENERATOR_ON = true;
	private final boolean ASTATIC_REGULATION_ON = true;
	private final boolean ASTATIC_REGULATION_OFF = false;
	private final LoadCurve NULL_CURVE = null;
	private Logger logger;

	public PowerStationGenerationScheduleCalculatorStub(TimeService timeService) {
		logger = LoggerFactory.getLogger(PowerStationGenerationScheduleCalculatorStub.class);
		
		if(timeService == null){
			logger.error("Null TimeService in constructor.");
			throw new IllegalArgumentException("PowerStationGenerationScheduleCalculatorStub"
					+ " constructor: timeService must not be null.");
		}
		
		this.timeService = timeService;
	}

	//TODO calculate schedule according to power station parameters and situations with consumers. 
	public PowerStationGenerationSchedule getSchedule(long powerStationId){
		return createSchedule(powerStationId);
	}
	
	private PowerStationGenerationSchedule createSchedule(long powerStationId){
		LoadCurve generationCurve;
		PowerStationGenerationSchedule generationSchedule;
		GeneratorGenerationSchedule genrationSchedule_1;
		GeneratorGenerationSchedule genrationSchedule_2;
		
		generationSchedule = new PowerStationGenerationSchedule(powerStationId,
				timeService.getCurrentDateTime(), LocalDateTime.MIN, GENERATORS_QUANTITY);
		generationCurve = new LoadCurve(PRECALCULATED_GENERATION_BY_HOURS);
		
		genrationSchedule_1 = new GeneratorGenerationSchedule(FIRST_GENERATOR_NUMBER,
				GENERATOR_ON, ASTATIC_REGULATION_ON, NULL_CURVE);
		
		genrationSchedule_2 = new GeneratorGenerationSchedule(SECOND_GENERATOR_NUMBER,
				GENERATOR_ON, ASTATIC_REGULATION_OFF, generationCurve);
		
		generationSchedule.addGeneratorSchedule(genrationSchedule_1);
		generationSchedule.addGeneratorSchedule(genrationSchedule_2);
		
		return generationSchedule;
	}
}
