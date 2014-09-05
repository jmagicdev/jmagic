package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Bant Battlemage")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = ShardsOfAlara.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.WHITE, Color.GREEN})
public final class BantBattlemage extends Card
{
	public static final class BantBattlemageAbility0 extends ActivatedAbility
	{
		public BantBattlemageAbility0(GameState state)
		{
			super(state, "(G), (T): Target creature gains trample until end of turn.");
			this.setManaCost(new ManaPool("(G)"));
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(addAbilityUntilEndOfTurn(target, org.rnd.jmagic.abilities.keywords.Trample.class, "Target creature gains trample until end of turn."));
		}
	}

	public static final class BantBattlemageAbility1 extends ActivatedAbility
	{
		public BantBattlemageAbility1(GameState state)
		{
			super(state, "(U), (T): Target creature gains flying until end of turn.");
			this.setManaCost(new ManaPool("(U)"));
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(addAbilityUntilEndOfTurn(target, org.rnd.jmagic.abilities.keywords.Flying.class, "Target creature gains flying until end of turn."));
		}
	}

	public BantBattlemage(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (G), (T): Target creature gains trample until end of turn.
		this.addAbility(new BantBattlemageAbility0(state));

		// (U), (T): Target creature gains flying until end of turn.
		this.addAbility(new BantBattlemageAbility1(state));
	}
}
