package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Tavern Swindler")
@Types({Type.CREATURE})
@SubTypes({SubType.ROGUE, SubType.HUMAN})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class TavernSwindler extends Card
{
	public static final class TavernSwindlerAbility0 extends ActivatedAbility
	{
		public TavernSwindlerAbility0(GameState state)
		{
			super(state, "(T), Pay 3 life: Flip a coin. If you win the flip, you gain 6 life.");
			this.costsTap = true;
			this.addCost(payLife(You.instance(), 3, "Pay 3 life"));

			EventFactory flip = new EventFactory(EventType.FLIP_COIN, "Flip a coin");
			flip.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(flip);

			SetGenerator won = Intersect.instance(Identity.instance(Answer.WIN), EffectResult.instance(flip));
			this.addEffect(ifThen(won, gainLife(You.instance(), 6, "You gain 6 life"), "If you win the flip, you gain 6 life."));
		}
	}

	public TavernSwindler(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (T), Pay 3 life: Flip a coin. If you win the flip, you gain 6 life.
		this.addAbility(new TavernSwindlerAbility0(state));
	}
}
