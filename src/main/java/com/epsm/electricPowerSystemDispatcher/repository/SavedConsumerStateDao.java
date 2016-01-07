package com.epsm.electricPowerSystemDispatcher.repository;

import java.util.List;

import com.epsm.electricPowerSystemDispatcher.model.domain.SavedConsumerState;

public interface SavedConsumerStateDao {
	public List<SavedConsumerState> getStatesByNumber(int consumerNumber);
	public void saveState(SavedConsumerState state);
}