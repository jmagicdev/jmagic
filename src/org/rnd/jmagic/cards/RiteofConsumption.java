package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Rite of Consumption")
@Types({Type.SORCERY})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Expansion.SHADOWMOOR, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class RiteofConsumption extends Card
{
	/**
	 * @eparam TARGET: the player targeted by rite of consumption
	 * @eparam NUMBER: amount of damage to deal
	 */
	public static final EventType RITE_OF_CONSUMPTION_EVENT = new EventType("RITE_OF_CONSUMPTION_EVENT")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			event.setResult(Empty.set);

			Set target = parameters.get(Parameter.TARGET);
			int amount = parameters.get(Parameter.NUMBER).getOne(Integer.class);
			Event damage = spellDealDamage(amount, Identity.instance(target), "Rite of Consumption deals damage equal to the sacrificed creature's power to target player.").createEvent(game, event.getSource());
			damage.perform(event, true);

			int life = damage.getDamage().size();
			gainLife(You.instance(), life, "You gain life equal to the damage dealt this way").createEvent(game, event.getSource()).perform(event, true);

			return true;
		}
	};

	public RiteofConsumption(GameState state)
	{
		super(state);

		// As an additional cost to cast Rite of Consumption, sacrifice a
		// creature.
		EventFactory sacACreature = sacrificeACreature();
		this.addCost(sacACreature);

		// Rite of Consumption deals damage equal to the sacrificed creature's
		// power to target player. You gain life equal to the damage dealt this
		// way.
		Target t = this.addTarget(Players.instance(), "target player");

		SetGenerator sacrificedCreature = OldObjectOf.instance(CostResult.instance(sacACreature));

		EventFactory effect = new EventFactory(RITE_OF_CONSUMPTION_EVENT, "Rite of Consumption deals damage equal to the sacrificed creature's power to target player. You gain life equal to the damage dealt this way.");
		effect.parameters.put(EventType.Parameter.TARGET, targetedBy(t));
		effect.parameters.put(EventType.Parameter.NUMBER, PowerOf.instance(sacrificedCreature));
		this.addEffect(effect);
	}
}
