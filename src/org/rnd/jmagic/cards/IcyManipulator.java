package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Icy Manipulator")
@Types({Type.ARTIFACT})
@ManaCost("4")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.MIRRODIN, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.ICE_AGE, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.UNLIMITED, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.BETA, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.ALPHA, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class IcyManipulator extends Card
{
	public static final class Freeze extends ActivatedAbility
	{
		public Freeze(GameState state)
		{
			super(state, "(1), (T): Tap target artifact, creature, or land.");

			this.setManaCost(new ManaPool("1"));

			this.costsTap = true;

			Target target = this.addTarget(Intersect.instance(InZone.instance(Battlefield.instance()), HasType.instance(Type.ARTIFACT, Type.CREATURE, Type.LAND)), "target artifact, creature, or land");
			this.addEffect(tap(targetedBy(target), "Tap target artifact, creature, or land."));
		}
	}

	public IcyManipulator(GameState state)
	{
		super(state);

		this.addAbility(new Freeze(state));
	}
}
