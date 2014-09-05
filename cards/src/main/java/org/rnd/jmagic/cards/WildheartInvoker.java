package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Wildheart Invoker")
@Types({Type.CREATURE})
@SubTypes({SubType.ELF, SubType.SHAMAN})
@ManaCost("2GG")
@Printings({@Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class WildheartInvoker extends Card
{
	public static final class WildheartInvokerAbility0 extends ActivatedAbility
	{
		public WildheartInvokerAbility0(GameState state)
		{
			super(state, "(8): Target creature gets +5/+5 and gains trample until end of turn.");
			this.setManaCost(new ManaPool("(8)"));

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(ptChangeAndAbilityUntilEndOfTurn(target, +5, +5, "Target creature gets +5/+5 and gains trample until end of turn.", org.rnd.jmagic.abilities.keywords.Trample.class));
		}
	}

	public WildheartInvoker(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(3);

		// (8): Target creature gets +5/+5 and gains trample until end of turn.
		this.addAbility(new WildheartInvokerAbility0(state));
	}
}
