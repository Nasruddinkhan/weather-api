package com.clairvoyant.assignment.weatherapi.mapper;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MapperUtil {
	private static ModelMapper modelMapper = new ModelMapper();

	static {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT)
				.setPropertyCondition(Conditions.isNotNull());
	}

	private MapperUtil() {
		super();
	}

	/**
	 *
	 * @param source
	 * @param destination
	 * @param <S>
	 * @param <D>
	 * @return
	 */
	public static <S, D> D map(final S source, Class<D> destination) {
		return modelMapper.map(source, destination);
	}

	/**
	 *
	 * @param source
	 * @param destination
	 * @param <S>
	 * @param <D>
	 * @return
	 */
	public static <S, D> List<D> mapAll(final Collection<S> source, Class<D> destination) {
		return source.stream().map(m -> map(m, destination)).collect(Collectors.toList());
	}

	/**
	 *
	 * @param source
	 * @param destination
	 * @param <S>
	 * @param <D>
	 * @return
	 */
	public static <S, D> Set<D> mapAll(final Set<S> source, Class<D> destination) {
		return source.stream().map(m -> map(m, destination)).collect(Collectors.toSet());
	}
}
