package org.rnd.jmagic.abilities.keywords;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public abstract class Landwalk extends Keyword
{
	private final SuperType superType;
	private final SubType landType;

	/**
	 * Says that the landwalk condition is reversed. Useful for things like
	 * nonbasic landwalk.
	 */
	private final boolean not;

	private Landwalk(GameState state, SuperType superType, SubType landType)
	{
		super(state, superType + " " + landType.toString().toLowerCase() + "walk");
		this.superType = superType;
		this.landType = landType;
		this.not = false;
	}

	private Landwalk(GameState state, SuperType type, boolean not)
	{
		super(state, (not ? "Non" + type.toString().toLowerCase() : type.toString()) + " landwalk");
		this.superType = type;
		this.landType = null;
		this.not = not;
	}

	private Landwalk(GameState state, SubType type)
	{
		super(state, type + "walk");
		this.superType = null;
		this.landType = type;
		this.not = false;
	}

	@Override
	protected java.util.List<StaticAbility> createStaticAbilities()
	{
		java.util.LinkedList<StaticAbility> ret = new java.util.LinkedList<StaticAbility>();
		ret.add(new LandwalkAbility(this.state, this.superType, this.landType, this.not));
		return ret;
	}

	public static final class LandwalkAbility extends StaticAbility
	{
		private final SuperType superType;
		private final SubType landType;
		private final boolean not;

		public LandwalkAbility(GameState state, SuperType superType, SubType landType, boolean not)
		{
			super(state, formatName(superType, landType, not));
			this.superType = superType;
			this.landType = landType;
			this.not = not;

			SetGenerator landFilter = HasType.instance(Type.LAND);

			if(landType != null)
				landFilter = Intersect.instance(HasSubType.instance(landType), landFilter);

			if(superType != null)
				landFilter = Intersect.instance(HasSuperType.instance(superType), landFilter);

			if(this.not)
				landFilter = Not.instance(landFilter);

			SetGenerator defendingPlayer = IsAttacked.instance(This.instance());
			SetGenerator playerHasLand = Intersect.instance(ControlledBy.instance(defendingPlayer), landFilter);
			SetGenerator thisIsBlocked = Blocking.instance(This.instance());
			SetGenerator playerHasLandAndThisIsBlocked = Both.instance(playerHasLand, thisIsBlocked);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(playerHasLandAndThisIsBlocked));
			this.addEffectPart(part);
		}

		@Override
		public LandwalkAbility create(Game game)
		{
			return new LandwalkAbility(game.physicalState, this.superType, this.landType, this.not);
		}

		private static String formatName(SuperType s, SubType l, boolean not)
		{
			String land = (not ? "non" : "") + (s == null ? "" : s.toString().toLowerCase() + " ") + (l == null ? "land" : l);

			return "This creature is unblockable as long as defending player controls a " + land;
		}
	}

	@Name("Plainswalk")
	public static final class Plainswalk extends org.rnd.jmagic.abilities.keywords.Landwalk
	{
		public Plainswalk(GameState state)
		{
			super(state, SubType.PLAINS);
		}
	}

	@Name("Islandwalk")
	public static final class Islandwalk extends org.rnd.jmagic.abilities.keywords.Landwalk
	{
		public Islandwalk(GameState state)
		{
			super(state, SubType.ISLAND);
		}
	}

	@Name("Swampwalk")
	public static final class Swampwalk extends org.rnd.jmagic.abilities.keywords.Landwalk
	{
		public Swampwalk(GameState state)
		{
			super(state, SubType.SWAMP);
		}
	}

	@Name("Mountainwalk")
	public static final class Mountainwalk extends org.rnd.jmagic.abilities.keywords.Landwalk
	{
		public Mountainwalk(GameState state)
		{
			super(state, SubType.MOUNTAIN);
		}
	}

	@Name("Forestwalk")
	public static final class Forestwalk extends Landwalk
	{
		public Forestwalk(GameState state)
		{
			super(state, SubType.FOREST);
		}
	}

	public static final class NonbasicLandwalk extends Landwalk
	{
		public NonbasicLandwalk(GameState state)
		{
			super(state, SuperType.BASIC, true);
		}
	}
}
