package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public abstract class TypeCycling extends CyclingBase
{
	protected Set filterTypes;
	protected String filterString;

	private CostCollection cost;

	private static String makeName(String typeString)
	{
		return typeString.substring(0, 1).toUpperCase() + typeString.substring(1) + "cycling";
	}

	public TypeCycling(GameState state, String manaCost, Set types, String typeString)
	{
		this(state, new CostCollection(Cycling.COST_TYPE, new ManaPool(manaCost)), types, typeString);
	}

	public TypeCycling(GameState state, CostCollection cost, Set types, String typeString)
	{
		super(state, makeName(typeString) + (cost.events.isEmpty() ? " " : "\u2014") + cost);
		this.filterTypes = types;
		this.filterString = typeString;

		this.cost = cost;
	}

	@Override
	protected CostCollection getCost()
	{
		return this.cost;
	}

	@Override
	protected java.util.List<CyclingAbilityBase<?>> createCyclingAbilities(GameState state)
	{
		java.util.List<CyclingAbilityBase<?>> ret = new java.util.LinkedList<CyclingAbilityBase<?>>();

		ret.add(new TypeCyclingAbility(state, this));

		return ret;
	}

	public static final class TypeCyclingAbility extends CyclingAbilityBase<TypeCycling>
	{
		public TypeCyclingAbility(GameState state, TypeCycling parent)
		{
			super(state, parent, parent.getCost() + ", discard this: Search your library for a " + parent.filterString + " card, reveal it, and put it into your hand. Then shuffle your library.");

			SetGenerator filter = null;
			if(parent.filterTypes != null)
			{
				java.util.Set<SuperType> superType = parent.filterTypes.getAll(SuperType.class);
				java.util.Set<Type> type = parent.filterTypes.getAll(Type.class);
				java.util.Set<SubType> subTypes = parent.filterTypes.getAll(SubType.class);

				if(!superType.isEmpty())
					filter = HasSuperType.instance(Identity.fromCollection(superType));
				if(!type.isEmpty())
					if(filter == null)
						filter = HasType.instance(Identity.fromCollection(type));
					else
						filter = Intersect.instance(filter, HasType.instance(Identity.fromCollection(type)));
				if(!subTypes.isEmpty())
					if(filter == null)
						filter = HasSubType.instance(Identity.fromCollection(subTypes));
					else
						filter = Intersect.instance(filter, HasSubType.instance(Identity.fromCollection(subTypes)));
			}

			EventType.ParameterMap searchParameters = new EventType.ParameterMap();
			searchParameters.put(EventType.Parameter.CAUSE, This.instance());
			searchParameters.put(EventType.Parameter.PLAYER, You.instance());
			searchParameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			searchParameters.put(EventType.Parameter.CARD, LibraryOf.instance(You.instance()));
			if(filter != null)
				searchParameters.put(EventType.Parameter.TYPE, Identity.instance(filter));
			searchParameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			this.addEffect(new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, searchParameters, ("Search your library for a " + parent.filterString + " card, reveal it, and put it into your hand. Then shuffle your library.")));

			this.activateOnlyFromHand();
		}

		@Override
		public TypeCyclingAbility create(Game game)
		{
			return new TypeCyclingAbility(game.physicalState, game.physicalState.<TypeCycling>get(this.parentID));
		}
	}

	public static final class BasicLandCycling extends TypeCycling
	{
		public BasicLandCycling(GameState state, String manaCost)
		{
			super(state, manaCost, new Set(SuperType.BASIC, Type.LAND), "basic land");
		}

		@Override
		public BasicLandCycling create(Game game)
		{
			return new BasicLandCycling(game.physicalState, this.getCost().manaCost.toString());
		}
	}

	public static final class PlainsCycling extends TypeCycling
	{
		public PlainsCycling(GameState state, String manaCost)
		{
			super(state, manaCost, new Set(SubType.PLAINS), "Plains");
		}

		@Override
		public PlainsCycling create(Game game)
		{
			return new PlainsCycling(game.physicalState, this.getCost().manaCost.toString());
		}
	}

	public static final class IslandCycling extends TypeCycling
	{
		public IslandCycling(GameState state, String manaCost)
		{
			super(state, manaCost, new Set(SubType.ISLAND), "Island");
		}

		@Override
		public IslandCycling create(Game game)
		{
			return new IslandCycling(game.physicalState, this.getCost().manaCost.toString());
		}
	}

	public static final class SwampCycling extends TypeCycling
	{
		public SwampCycling(GameState state, String manaCost)
		{
			super(state, manaCost, new Set(SubType.SWAMP), "Swamp");
		}

		@Override
		public SwampCycling create(Game game)
		{
			return new SwampCycling(game.physicalState, this.getCost().manaCost.toString());
		}
	}

	public static final class MountainCycling extends TypeCycling
	{
		public MountainCycling(GameState state, String manaCost)
		{
			super(state, manaCost, new Set(SubType.MOUNTAIN), "Mountain");
		}

		@Override
		public MountainCycling create(Game game)
		{
			return new MountainCycling(game.physicalState, this.getCost().manaCost.toString());
		}
	}

	public static final class ForestCycling extends TypeCycling
	{
		public ForestCycling(GameState state, String manaCost)
		{
			super(state, manaCost, new Set(SubType.FOREST), "Forest");
		}

		@Override
		public ForestCycling create(Game game)
		{
			return new ForestCycling(game.physicalState, this.getCost().manaCost.toString());
		}
	}
}
