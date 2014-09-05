package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Royal Assassin")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.ASSASSIN})
@ManaCost("1BB")
@Printings({@Printings.Printed(ex = Magic2012.class, r = Rarity.RARE), @Printings.Printed(ex = Magic2011.class, r = Rarity.RARE), @Printings.Printed(ex = Magic2010.class, r = Rarity.RARE), @Printings.Printed(ex = TenthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = NinthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = EighthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = FourthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = RevisedEdition.class, r = Rarity.RARE), @Printings.Printed(ex = UnlimitedEdition.class, r = Rarity.RARE), @Printings.Printed(ex = LimitedEditionBeta.class, r = Rarity.RARE), @Printings.Printed(ex = LimitedEditionAlpha.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class RoyalAssassin extends Card
{
	public static final class Assassinate extends ActivatedAbility
	{
		public Assassinate(GameState state)
		{
			super(state, "(T): Destroy target tapped creature.");

			this.costsTap = true;
			this.addEffect(destroy(Intersect.instance(Tapped.instance(), HasType.instance(Type.CREATURE)), "Destroy target tapped creature."));
		}
	}

	public RoyalAssassin(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new Assassinate(state));
	}
}
