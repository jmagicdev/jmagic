package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Vengevine")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("2GG")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.MYTHIC)})
@ColorIdentity({Color.GREEN})
public final class Vengevine extends Card
{
	/**
	 * Keys are player IDs, values are IDs of the creature spells that player
	 * has cast this turn, in order
	 */
	public static final class CreatureSpells extends Tracker<java.util.Map<Integer, java.util.List<Integer>>>
	{
		private java.util.HashMap<Integer, java.util.List<Integer>> value = new java.util.HashMap<Integer, java.util.List<Integer>>();
		private java.util.Map<Integer, java.util.List<Integer>> unmodifiable = java.util.Collections.unmodifiableMap(this.value);

		@Override
		@SuppressWarnings("unchecked")
		public CreatureSpells clone()
		{
			CreatureSpells ret = (CreatureSpells)super.clone();
			ret.value = (java.util.HashMap<Integer, java.util.List<Integer>>)this.value.clone();
			ret.unmodifiable = java.util.Collections.unmodifiableMap(ret.value);
			return ret;
		}

		@Override
		protected java.util.Map<Integer, java.util.List<Integer>> getValueInternal()
		{
			return this.unmodifiable;
		}

		@Override
		protected boolean match(GameState state, Event event)
		{
			if(event.type != EventType.BECOMES_PLAYED)
				return false;

			GameObject played = event.parameters.get(EventType.Parameter.OBJECT).evaluate(state, null).getOne(GameObject.class);
			return played.getTypes().contains(Type.CREATURE) && played.isSpell();
		}

		@Override
		protected void reset()
		{
			this.value.clear();
		}

		@Override
		protected void update(GameState state, Event event)
		{
			GameObject played = event.parameters.get(EventType.Parameter.OBJECT).evaluate(state, null).getOne(GameObject.class);
			Player who = event.parameters.get(EventType.Parameter.PLAYER).evaluate(state, null).getOne(Player.class);

			if(!this.value.containsKey(who.ID))
				this.value.put(who.ID, new java.util.LinkedList<Integer>());
			this.value.get(who.ID).add(played.ID);
		}
	}

	public static final class SecondCreatureSpell extends SetGenerator
	{
		private SetGenerator who;

		public static SecondCreatureSpell instance(SetGenerator who)
		{
			return new SecondCreatureSpell(who);
		}

		private SecondCreatureSpell(SetGenerator who)
		{
			this.who = who;
		}

		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			java.util.Map<Integer, java.util.List<Integer>> flagValue = state.getTracker(CreatureSpells.class).getValue(state);
			Set who = this.who.evaluate(state, thisObject);

			Set ret = new Set();
			for(Player player: who.getAll(Player.class))
				if(flagValue.containsKey(player.ID))
				{
					java.util.List<Integer> thatPlayersSpells = flagValue.get(player.ID);
					if(thatPlayersSpells.size() >= 2)
						ret.add(state.get(thatPlayersSpells.get(1)));
				}
			return ret;
		}
	}

	public static final class Vengeful extends EventTriggeredAbility
	{
		public Vengeful(GameState state)
		{
			super(state, "Whenever you cast a spell, if it's the second creature spell you cast this turn, you may return Vengevine from your graveyard to the battlefield.");

			this.addPattern(whenYouCastASpell());

			state.ensureTracker(new CreatureSpells());
			SetGenerator thatSpell = EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.OBJECT);
			SetGenerator yourSecondCreature = SecondCreatureSpell.instance(You.instance());
			this.interveningIf = Intersect.instance(thatSpell, yourSecondCreature);

			EventFactory putOntoBattlefield = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD, "Return Vengevine from your graveyard to the battlefield");
			putOntoBattlefield.parameters.put(EventType.Parameter.CAUSE, This.instance());
			putOntoBattlefield.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			putOntoBattlefield.parameters.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			this.addEffect(youMay(putOntoBattlefield, "You may return Vengevine from your graveyard to the battlefield."));

			this.triggersFromGraveyard();
		}
	}

	public Vengevine(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(3);

		// Haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// Whenever you cast a spell, if it's the second creature spell you cast
		// this turn, you may return Vengevine from your graveyard to the
		// battlefield.
		this.addAbility(new Vengeful(state));
	}
}
