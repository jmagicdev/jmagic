package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Etched Monstrosity")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.GOLEM})
@ManaCost("5")
@Printings({@Printings.Printed(ex = NewPhyrexia.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLUE, Color.WHITE, Color.GREEN, Color.BLACK, Color.RED})
public final class EtchedMonstrosity extends Card
{
	public static final class EtchedMonstrosityAbility1 extends ActivatedAbility
	{
		public EtchedMonstrosityAbility1(GameState state)
		{
			super(state, "(W)(U)(B)(R)(G), Remove five -1/-1 counters from Etched Monstrosity: Target player draws three cards.");
			this.setManaCost(new ManaPool("(W)(U)(B)(R)(G)"));
			this.addCost(removeCounters(5, Counter.CounterType.MINUS_ONE_MINUS_ONE, ABILITY_SOURCE_OF_THIS, "Remove five -1/-1 counters from Etched Monstrosity"));
			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			this.addEffect(drawCards(target, 3, "Target player draws three cards."));
		}
	}

	public EtchedMonstrosity(GameState state)
	{
		super(state);

		this.setPower(10);
		this.setToughness(10);

		// Etched Monstrosity enters the battlefield with five -1/-1 counters on
		// it.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldWithCounters(state, this.getName(), 5, Counter.CounterType.MINUS_ONE_MINUS_ONE));

		// (W)(U)(B)(R)(G), Remove five -1/-1 counters from Etched Monstrosity:
		// Target player draws three cards.
		this.addAbility(new EtchedMonstrosityAbility1(state));
	}
}
