/*
 * Copyright (c) 2015 Villu Ruusmann
 *
 * This file is part of JPMML-SkLearn
 *
 * JPMML-SkLearn is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JPMML-SkLearn is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with JPMML-SkLearn.  If not, see <http://www.gnu.org/licenses/>.
 */
package sklearn;

import java.util.Collections;
import java.util.Set;

import org.dmg.pmml.DataType;
import org.dmg.pmml.DefineFunction;
import org.dmg.pmml.Model;
import org.dmg.pmml.OpType;
import org.jpmml.converter.Schema;
import org.jpmml.converter.ValueUtil;
import org.jpmml.sklearn.SkLearnEncoder;

abstract
public class Estimator extends BaseEstimator implements HasNumberOfFeatures {

	public Estimator(String module, String name){
		super(module, name);
	}

	abstract
	public boolean isSupervised();

	abstract
	public Model encodeModel(Schema schema);

	public Model encodeModel(Schema schema, SkLearnEncoder encoder){
		schema = encoder.cast(requiresContinuousInput() ? OpType.CONTINUOUS : null, getDataType(), schema);

		return encodeModel(schema);
	}

	@Override
	public int getNumberOfFeatures(){
		return ValueUtil.asInt((Number)get("n_features_"));
	}

	public boolean requiresContinuousInput(){
		return true;
	}

	/**
	 * The {@link OpType operational type} of active fields.
	 */
	public OpType getOpType(){
		return OpType.CONTINUOUS;
	}

	/**
	 * The {@link DataType data type} of active fields.
	 */
	public DataType getDataType(){
		return DataType.DOUBLE;
	}

	public Set<DefineFunction> encodeDefineFunctions(){
		return Collections.emptySet();
	}
}