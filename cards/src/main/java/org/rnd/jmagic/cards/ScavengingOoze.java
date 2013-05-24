package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Scavenging Ooze")
@Types({Type.CREATURE})
@SubTypes({SubType.OOZE})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class ScavengingOoze extends Card
{
	public static final class ScavengingOozeAbility0 extends ActivatedAbility
	{
		public ScavengingOozeAbility0(GameState state)
		{
			super(state, "(G): Exile target card from a graveyard. If it was a creature card, put a +1/+1 counter on Scavenging Ooze and you gain 1 life.");
			this.setManaCost(new ManaPool("(G)"));

			SetGenerator target = targetedBy(this.addTarget(InZone.instance(GraveyardOf.instance(Players.instance())), "target card in a graveyard"));

			this.addEffect(exile(target, "Exile target card from a graveyard."));

			EventFactory factory = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "If it was a creature card, put a +1/+1 counter on Scavenging Ooze and you gain 1 life.");
			factory.parameters.put(EventType.Parameter.IF, Intersect.instance(target, HasType.instance(Type.CREATURE)));
			factory.parameters.put(EventType.Parameter.THEN, Identity.instance(simultaneous(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, ABILITY_SOURCE_OF_THIS, "Put a +1/+1 counter on Scavenging Ooze"), gainLife(You.instance(), 1, "You gain 1 life."))));
			this.addEffect(factory);
		}
	}

	public ScavengingOoze(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (G): Exile target card from a graveyard. If it was a creature card,
		// put a +1/+1 counter on Scavenging Ooze and you gain 1 life.
		this.addAbility(new ScavengingOozeAbility0(state));
	}
}
