package org.rnd.jmagic.abilities.keywords;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public abstract class Enchant extends Keyword
{
	public static final class Final extends Enchant
	{
		public Final(GameState state, String filterName, SetGenerator filter)
		{
			super(state, filterName, filter);
		}

		@Override
		public Final create(Game game)
		{
			return new Final(game.physicalState, this.filterName, this.filter);
		}
	}

	public static final class Artifact extends Enchant
	{
		public Artifact(GameState state)
		{
			super(state, "artifact", ArtifactPermanents.instance());
		}
	}

	public static final class Creature extends Enchant
	{
		public Creature(GameState state)
		{
			super(state, "creature", CreaturePermanents.instance());
		}
	}

	public static final class CreatureYouControl extends Enchant
	{
		public CreatureYouControl(GameState state)
		{
			super(state, "creature you control", org.rnd.jmagic.Convenience.CREATURES_YOU_CONTROL);
		}
	}

	public static final class Land extends Enchant
	{
		public Land(GameState state)
		{
			super(state, "land", LandPermanents.instance());
		}
	}

	public static final class NonLandPermanent extends Enchant
	{
		public NonLandPermanent(GameState state)
		{
			super(state, "nonland permanent", RelativeComplement.instance(Permanents.instance(), HasType.instance(Type.LAND)));
		}
	}

	public static final class Player extends Enchant
	{
		public Player(GameState state)
		{
			super(state, "player", Players.instance());
		}
	}

	public static final class Permanent extends Enchant
	{
		public Permanent(GameState state)
		{
			super(state, "permanent", Permanents.instance());
		}
	}

	public static final class TappedCreature extends Enchant
	{
		public TappedCreature(GameState state)
		{
			super(state, "tapped creature", Intersect.instance(CreaturePermanents.instance(), Tapped.instance()));
		}
	}

	public final String filterName;
	public final SetGenerator filter;

	public Enchant(GameState state, String filterName, SetGenerator filter)
	{
		super(state, "Enchant " + filterName);
		this.filterName = filterName;
		this.filter = filter;
	}

	/**
	 * TODO : In light of 702.5c (quoted below), this is the wrong way to go
	 * about adding a target to an Aura spell.
	 * 
	 * 702.5c If an Aura has multiple instances of enchant, all of them apply.
	 * The Aura's target must follow the restrictions from all the instances of
	 * enchant. The Aura can enchant only objects or players that match all of
	 * its enchant abilities.
	 * 
	 * I think the right way to do this is to put code in Card.selectTargets.
	 */
	@Override
	public void applyHook(GameObject source)
	{
		source.addTarget(this.filter, "target " + this.filterName);
	}

	/** @return True. */
	@Override
	public final boolean isEnchant()
	{
		return true;
	}

	@Override
	public void removeHook(GameObject source)
	{
		source.clearTargets();
	}
}
