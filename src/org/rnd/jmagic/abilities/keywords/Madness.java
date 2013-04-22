package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

/**
 * 702.32. Madness
 * 
 * 702.32a Madness is a keyword that represents two abilities. The first is a
 * static ability that functions while the card with madness is in a player's
 * hand. The second is a triggered ability that functions when the first ability
 * is applied. "Madness [cost]" means "If a player would discard this card, that
 * player discards it, but may exile it instead of putting it into his or her
 * graveyard" and "When this card is exiled this way, its owner may cast it by
 * paying [cost] rather than paying its mana cost. If that player doesn't, he or
 * she puts this card into his or her graveyard."
 * 
 * 702.32b Casting a spell using its madness ability follows the rules for
 * paying alternative costs in rules 601.2b and 601.2e-g.
 */
public final class Madness extends Keyword
{
	public static final Object MADNESS_MANA = "Madness";
	public static final Object MADNESS_FREE = "Madness (0)";

	private final CostCollection costs;

	public Madness(GameState state, String mana)
	{
		this(state, new CostCollection(MADNESS_MANA, mana));
	}

	public Madness(GameState state)
	{
		this(state, new CostCollection(MADNESS_FREE));
	}

	public Madness(GameState state, CostCollection costs)
	{
		super(state, "Madness " + costs.toString());
		this.costs = costs;
	}

	@Override
	public java.util.List<StaticAbility> createStaticAbilities()
	{
		return java.util.Collections.<StaticAbility>singletonList(new MadnessStatic(this.state));
	}

	@Override
	public java.util.List<NonStaticAbility> createNonStaticAbilities()
	{
		return java.util.Collections.<NonStaticAbility>singletonList(new MadnessTrigger(this.state, this.costs));
	}

	public static final class MadnessStatic extends StaticAbility
	{
		public static final class MadnessZoneChangeReplacementEffect extends ZoneChangeReplacementEffect
		{
			public MadnessZoneChangeReplacementEffect(Game game, String name)
			{
				super(game, name);
			}
		}

		public MadnessStatic(GameState state)
		{
			super(state, "If a player would discard this card, that player discards it, but may exile it instead of putting it into his or her graveyard.");

			ZoneChangeReplacementEffect replacement = new MadnessZoneChangeReplacementEffect(state.game, "If a player would discard this card, that player discards it, but may exile it instead of putting it into his or her graveyard.");
			replacement.addPattern(new org.rnd.jmagic.engine.patterns.DiscardPattern(This.instance()));
			replacement.optional = ControllerOf.instance(This.instance());
			replacement.changeDestination(ExileZone.instance());

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.REPLACEMENT_EFFECT);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Identity.instance(replacement));
			this.addEffectPart(part);

			this.canApply = Intersect.instance(This.instance(), InZone.instance(HandOf.instance(Players.instance())));
		}
	}

	public static final class MadnessTrigger extends EventTriggeredAbility
	{
		public static final class MadnessPattern implements ZoneChangePattern
		{
			private final int parentID;

			public MadnessPattern(MadnessTrigger parent)
			{
				this.parentID = parent.ID;
			}

			@Override
			public boolean looksBackInTime()
			{
				return false;
			}

			@Override
			public boolean match(ZoneChange zoneChange, Identified thisObject, GameState state)
			{
				if(state.exileZone().ID != zoneChange.destinationZoneID)
					return false;

				GameObject newObject = state.get(zoneChange.newObjectID);
				if(!newObject.getAbilityIDsInOrder().contains(this.parentID))
					return false;

				for(ZoneChangeReplacementEffect replacement: zoneChange.replacedBy)
					if(replacement instanceof MadnessStatic.MadnessZoneChangeReplacementEffect)
						return true;
				return false;
			}
		}

		private final CostCollection madnessCosts;

		public MadnessTrigger(GameState state, CostCollection costs)
		{
			super(state, "When this card is exiled this way, its owner may cast it by paying " + costs + " rather than paying its mana cost. If that player doesn't, he or she puts this card into his or her graveyard.");

			this.addPattern(new MadnessPattern(this));

			this.madnessCosts = costs;
			String costString = costs.toString();

			java.util.Set<Object> newCosts = new java.util.HashSet<Object>();
			newCosts.addAll(costs.events);
			newCosts.addAll(costs.manaCost);

			SetGenerator newObject = NewObjectOf.instance(TriggerZoneChange.instance(This.instance()));
			SetGenerator owner = OwnerOf.instance(newObject);

			EventFactory cast = new EventFactory(EventType.CAST_SPELL_OR_ACTIVATE_ABILITY, "Cast it by paying " + costString + " rather than paying its mana cost.");
			cast.parameters.put(EventType.Parameter.PLAYER, owner);
			cast.parameters.put(EventType.Parameter.ALTERNATE_COST, Identity.instance(newCosts));
			cast.parameters.put(EventType.Parameter.OBJECT, newObject);

			EventFactory mayCast = playerMay(owner, cast, "Its owner may cast it by paying " + costString + " rather than paying its mana cost.");

			EventFactory move = new EventFactory(EventType.PUT_INTO_GRAVEYARD, "Put this card into its owner's graveyard.");
			move.parameters.put(EventType.Parameter.CAUSE, This.instance());
			move.parameters.put(EventType.Parameter.OBJECT, newObject);

			EventFactory effect = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "Its owner may cast it by paying " + costString + " rather than paying its mana cost. If that player doesn't, he or she puts this card into his or her graveyard.");
			effect.parameters.put(EventType.Parameter.IF, Identity.instance(mayCast));
			effect.parameters.put(EventType.Parameter.ELSE, Identity.instance(move));
			this.addEffect(effect);

			this.canTrigger = NonEmpty.instance();
		}

		@Override
		public MadnessTrigger create(Game game)
		{
			return new MadnessTrigger(game.physicalState, this.madnessCosts);
		}
	}
}
