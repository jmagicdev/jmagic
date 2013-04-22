package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Loxodon Mystic")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEPHANT, SubType.CLERIC})
@ManaCost("3WW")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.DARKSTEEL, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class LoxodonMystic extends Card
{
	public static final class TapDown extends ActivatedAbility
	{
		public TapDown(GameState state)
		{
			super(state, "(W), (T): Tap target creature.");

			this.setManaCost(new ManaPool("W"));

			this.costsTap = true;

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
			this.addEffect(tap(targetedBy(target), "Tap target creature."));
		}
	}

	public LoxodonMystic(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		this.addAbility(new TapDown(state));
	}
}
