package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Somberwald Vigilante")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WARRIOR})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class SomberwaldVigilante extends Card
{
	public static final class SomberwaldVigilanteAbility0 extends EventTriggeredAbility
	{
		public SomberwaldVigilanteAbility0(GameState state)
		{
			super(state, "Whenever Somberwald Vigilante becomes blocked by a creature, Somberwald Vigilante deals 1 damage to that creature.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_BLOCKED_BY_ONE);
			pattern.put(EventType.Parameter.ATTACKER, ABILITY_SOURCE_OF_THIS);
			pattern.put(EventType.Parameter.DEFENDER, CreaturePermanents.instance());
			this.addPattern(pattern);

			SetGenerator taker = EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.DEFENDER);
			this.addEffect(permanentDealDamage(1, taker, "Somberwald Vigilante deals 1 damage to that creature."));
		}
	}

	public SomberwaldVigilante(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Whenever Somberwald Vigilante becomes blocked by a creature,
		// Somberwald Vigilante deals 1 damage to that creature.
		this.addAbility(new SomberwaldVigilanteAbility0(state));
	}
}
