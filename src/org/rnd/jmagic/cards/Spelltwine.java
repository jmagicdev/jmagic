package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.Set;
import org.rnd.jmagic.engine.generators.*;

@Name("Spelltwine")
@Types({Type.SORCERY})
@ManaCost("5U")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class Spelltwine extends Card
{
	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("Spelltwine", "Order the spells to cast.", true);

	/**
	 * @eparam CAUSE: The cause.
	 * @eparam OBJECT: The spells to cast without paying their mana cost, if
	 * able.
	 * @eparam PLAYER: The player casting the spells.
	 * @eparam RESULT: Empty.
	 */
	public static final EventType CAST_BOTH = new EventType("CAST_BOTH")
	{
		@Override
		public Parameter affects()
		{
			return EventType.Parameter.OBJECT;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			boolean success = true;

			Set spells = parameters.get(Parameter.OBJECT);
			java.util.Map<GameObject, Event> castable = new java.util.HashMap<GameObject, Event>();

			Set playerSet = parameters.get(Parameter.PLAYER);

			for(GameObject spell: spells.getAll(GameObject.class))
			{
				java.util.Map<Parameter, Set> castParameters = new java.util.HashMap<Parameter, Set>();
				castParameters.put(EventType.Parameter.PLAYER, playerSet);
				castParameters.put(EventType.Parameter.ALTERNATE_COST, new Set());
				castParameters.put(EventType.Parameter.OBJECT, new Set(spell));
				Event cast = createEvent(game, "Cast " + spell + ".", EventType.CAST_SPELL_OR_ACTIVATE_ABILITY, castParameters);
				if(cast.attempt(event))
					castable.put(spell, cast);
			}

			int size = castable.size();
			if(size > 0)
			{
				Player player = playerSet.getOne(Player.class);

				java.util.List<GameObject> ordered = new java.util.LinkedList<GameObject>();

				if(size > 1)
					ordered = player.sanitizeAndChoose(game.actualState, size, castable.keySet(), PlayerInterface.ChoiceType.OBJECTS_ORDERED, REASON);
				else
					ordered.addAll(castable.keySet());

				for(GameObject spell: ordered)
				{
					Event e = castable.get(spell);
					if(!e.perform(event, false))
						success = false;
				}
			}

			event.setResult(Identity.instance());

			return success;
		}
	};

	public Spelltwine(GameState state)
	{
		super(state);

		// Exile target instant or sorcery card from your graveyard and target
		// instant or sorcery card from an opponent's graveyard. Copy those
		// cards. Cast the copies if able without paying their mana costs. Exile
		// Spelltwine.
		SetGenerator spells = HasType.instance(Type.INSTANT, Type.SORCERY);
		SetGenerator targetOne = targetedBy(this.addTarget(Intersect.instance(spells, InZone.instance(GraveyardOf.instance(You.instance()))), "target instant or sorcery card in your graveyard"));
		SetGenerator targetTwo = targetedBy(this.addTarget(Intersect.instance(spells, InZone.instance(GraveyardOf.instance(OpponentsOf.instance(You.instance())))), "target instant or sorcery card in an opponent's graveyard"));

		EventFactory exile = exile(Union.instance(targetOne, targetTwo), "Exile target instant or sorcery card from your graveyard and target instant or sorcery card from an opponent's graveyard.");
		this.addEffect(exile);

		EventFactory copy = new EventFactory(EventType.COPY_SPELL_OR_ABILITY, "Copy those cards.");
		copy.parameters.put(EventType.Parameter.CAUSE, This.instance());
		copy.parameters.put(EventType.Parameter.OBJECT, NewObjectOf.instance(EffectResult.instance(exile)));
		copy.parameters.put(EventType.Parameter.PLAYER, You.instance());
		copy.parameters.put(EventType.Parameter.TARGET, Empty.instance());
		this.addEffect(copy);

		EventFactory cast = new EventFactory(CAST_BOTH, "Cast the copies if able without paying their mana costs.");
		cast.parameters.put(EventType.Parameter.CAUSE, This.instance());
		cast.parameters.put(EventType.Parameter.OBJECT, EffectResult.instance(copy));
		cast.parameters.put(EventType.Parameter.PLAYER, You.instance());
		this.addEffect(cast);
	}
}
