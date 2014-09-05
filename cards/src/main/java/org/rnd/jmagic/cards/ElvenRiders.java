package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Elven Riders")
@Types({Type.CREATURE})
@SubTypes({SubType.ELF})
@ManaCost("3GG")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Onslaught.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = ClassicSixthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = FifthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = FourthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Legends.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class ElvenRiders extends Card
{
	public static final class Riding extends StaticAbility
	{
		public Riding(GameState state)
		{
			super(state, "Elven Riders can't be blocked except by Walls and/or creatures with flying.");

			SetGenerator hasFlyingOrWall = Union.instance(HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class), HasSubType.instance(SubType.WALL));
			SetGenerator notBlockingWithFlyingOrWall = RelativeComplement.instance(Blocking.instance(This.instance()), hasFlyingOrWall);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(notBlockingWithFlyingOrWall));
			this.addEffectPart(part);
		}
	}

	public ElvenRiders(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		this.addAbility(new Riding(state));
	}
}
