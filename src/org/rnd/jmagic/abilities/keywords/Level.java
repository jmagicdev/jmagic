package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class Level extends Keyword
{
	public static final class LevelAbility extends StaticAbility
	{
		private final Class<?>[] abilities;
		private final Integer maximumLevel;
		private final int minimumLevel;
		private final int power;
		private final int toughness;

		public LevelAbility(GameState state, String name, int minimumLevel, Integer maximumLevel, int power, int toughness, Class<?>... abilities)
		{
			super(state, name);
			this.abilities = abilities;
			this.maximumLevel = maximumLevel;
			this.minimumLevel = minimumLevel;
			this.power = power;
			this.toughness = toughness;

			this.addEffectPart(setPowerAndToughness(This.instance(), power, toughness));
			if(null != abilities)
				this.addEffectPart(addAbilityToObject(This.instance(), abilities));

			this.canApply = Both.instance(this.canApply, Intersect.instance(Count.instance(CountersOn.instance(This.instance(), Counter.CounterType.LEVEL)), Between.instance(minimumLevel, maximumLevel)));
		}

		@Override
		public LevelAbility create(Game game)
		{
			return new LevelAbility(game.physicalState, this.getName(), this.minimumLevel, this.maximumLevel, this.power, this.toughness, this.abilities);
		}
	}

	private final Class<?>[] abilities;
	private final String abilityText;
	private final String keywordName;
	private final Integer maximumLevel;
	private final int minimumLevel;
	private final int power;
	private final int toughness;

	/**
	 * Constructs a "Level X+ -- [P/T]" ability.
	 * 
	 * @param state The game state in which to construct this ability.
	 * @param minimumLevel X.
	 * @param power P.
	 * @param toughness T.
	 */
	public Level(GameState state, int minimumLevel, int power, int toughness)
	{
		super(state, "Level " + minimumLevel + "+ \u2014 " + power + "/" + toughness);

		this.abilities = null;
		this.abilityText = null;
		this.keywordName = "As long as this creature has " + minimumLevel + " or more level counters on it, it's " + power + "/" + toughness + ".";
		this.minimumLevel = minimumLevel;
		this.maximumLevel = null;
		this.power = power;
		this.toughness = toughness;
	}

	/**
	 * Constructs a "Level X+ -- abilities, [P/T]" ability.
	 * 
	 * @param state The game state in which to construct this ability.
	 * @param minimumLevel X.
	 * @param power P.
	 * @param toughness T.
	 * @param abilityText The texts of the abilities to grant, separated by
	 * semicolons.
	 * @param abilities The classes of the abilities to grant.
	 */
	public Level(GameState state, int minimumLevel, int power, int toughness, String abilityText, Class<?>... abilities)
	{
		super(state, "Level " + minimumLevel + "+ \u2014 " + power + "/" + toughness + "; " + abilityText);

		this.abilities = abilities;
		this.abilityText = abilityText;
		this.keywordName = "As long as this creature has " + minimumLevel + " or more level counters on it, it's " + power + "/" + toughness + " and has " + abilityText + ".";
		this.minimumLevel = minimumLevel;
		this.maximumLevel = null;
		this.power = power;
		this.toughness = toughness;
	}

	/**
	 * Constructs a "Level X-Y -- [P/T]" ability.
	 * 
	 * @param state The game state in which to construct this ability.
	 * @param minimumLevel X.
	 * @param maximumLevel Y.
	 * @param power P.
	 * @param toughness T.
	 */
	public Level(GameState state, int minimumLevel, int maximumLevel, int power, int toughness)
	{
		super(state, "Level " + minimumLevel + "-" + maximumLevel + " \u2014 " + power + "/" + toughness);

		this.abilityText = null;
		this.abilities = null;
		this.keywordName = "As long as this creature has at least " + minimumLevel + " level counters on it, but no more than " + maximumLevel + " level counters on it, it's " + power + "/" + toughness + ".";
		this.minimumLevel = minimumLevel;
		this.maximumLevel = maximumLevel;
		this.power = power;
		this.toughness = toughness;
	}

	/**
	 * Constructs a "Level X-Y -- abilities, [P/T]" ability.
	 * 
	 * @param state The game state in which to construct this ability.
	 * @param minimumLevel X.
	 * @param maximumLevel Y.
	 * @param power P.
	 * @param toughness T.
	 * @param abilityText The texts of the abilities to grant, separated by
	 * semicolons.
	 * @param abilities The classes of the abilities to grant.
	 */
	public Level(GameState state, int minimumLevel, int maximumLevel, int power, int toughness, String abilityText, Class<?>... abilities)
	{
		super(state, "Level " + minimumLevel + "-" + maximumLevel + " \u2014 " + power + "/" + toughness + "; " + abilityText);

		this.abilities = abilities;
		this.abilityText = abilityText;
		this.keywordName = "As long as this creature has at least " + minimumLevel + " level counters on it, but no more than " + maximumLevel + " level counters on it, it's " + power + "/" + toughness + " and has " + abilityText + ".";
		this.minimumLevel = minimumLevel;
		this.maximumLevel = maximumLevel;
		this.power = power;
		this.toughness = toughness;
	}

	@Override
	public Level create(Game game)
	{
		if(null == this.maximumLevel)
		{
			if(null == this.abilities)
				return new Level(game.physicalState, this.minimumLevel, this.power, this.toughness);
			return new Level(game.physicalState, this.minimumLevel, this.power, this.toughness, this.abilityText, this.abilities);
		}
		if(null == this.abilities)
			return new Level(game.physicalState, this.minimumLevel, this.maximumLevel, this.power, this.toughness);
		return new Level(game.physicalState, this.minimumLevel, this.maximumLevel, this.power, this.toughness, this.abilityText, this.abilities);
	}

	@Override
	public boolean isLevelSymbol()
	{
		return true;
	}

	@Override
	protected java.util.List<StaticAbility> createStaticAbilities()
	{
		java.util.LinkedList<StaticAbility> ret = new java.util.LinkedList<StaticAbility>();
		ret.add(new LevelAbility(this.state, this.keywordName, this.minimumLevel, this.maximumLevel, this.power, this.toughness, this.abilities));
		return ret;
	}
}
