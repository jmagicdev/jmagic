package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Crovax, Ascendant Hero")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN})
@ManaCost("4WW")
@Printings({@Printings.Printed(ex = Expansion.PLANAR_CHAOS, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class CrovaxAscendantHero extends Card
{
	public static final class CrovaxAscendantHeroAbility2 extends ActivatedAbility
	{
		public CrovaxAscendantHeroAbility2(GameState state)
		{
			super(state, "Pay 2 life: Return Crovax, Ascendant Hero to its owner's hand.");
			this.addCost(payLife(You.instance(), 2, "Pay 2 life"));
			this.addEffect(bounce(ABILITY_SOURCE_OF_THIS, "Return Crovax, Ascendant Hero to its owner's hand."));
		}
	}

	public CrovaxAscendantHero(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Other white creatures get +1/+1.
		SetGenerator whiteCreatures = Intersect.instance(CreaturePermanents.instance(), HasColor.instance(Color.WHITE));
		SetGenerator otherWhiteCreatures = RelativeComplement.instance(whiteCreatures, This.instance());
		this.addAbility(new org.rnd.jmagic.abilities.StaticPTChange(state, otherWhiteCreatures, "Other white creatures", +1, +1, true));

		// Nonwhite creatures get -1/-1.
		SetGenerator nonwhiteCreatures = RelativeComplement.instance(CreaturePermanents.instance(), HasColor.instance(Color.WHITE));
		this.addAbility(new org.rnd.jmagic.abilities.StaticPTChange(state, nonwhiteCreatures, "Nonwhite creatures", -1, -1, true));

		// Pay 2 life: Return Crovax, Ascendant Hero to its owner's hand.
		this.addAbility(new CrovaxAscendantHeroAbility2(state));
	}
}
