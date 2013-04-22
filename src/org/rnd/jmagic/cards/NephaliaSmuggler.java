package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Nephalia Smuggler")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.ROGUE})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class NephaliaSmuggler extends Card
{
	public static final class NephaliaSmugglerAbility0 extends ActivatedAbility
	{
		public NephaliaSmugglerAbility0(GameState state)
		{
			super(state, "(3)(U), (T): Exile another target creature you control, then return that card to the battlefield under your control.");
			this.setManaCost(new ManaPool("(3)(U)"));
			this.costsTap = true;

			SetGenerator anotherCreature = RelativeComplement.instance(CREATURES_YOU_CONTROL, ABILITY_SOURCE_OF_THIS);
			SetGenerator target = targetedBy(this.addTarget(anotherCreature, "another target creature you control"));

			EventFactory blink = new EventFactory(BLINK, "Exile another target creature you control, then return that card to the battlefield under your control.");
			blink.parameters.put(EventType.Parameter.CAUSE, This.instance());
			blink.parameters.put(EventType.Parameter.TARGET, target);
			blink.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(blink);
		}
	}

	public NephaliaSmuggler(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (3)(U), (T): Exile another target creature you control, then return
		// that card to the battlefield under your control.
		this.addAbility(new NephaliaSmugglerAbility0(state));
	}
}
