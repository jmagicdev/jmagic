package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Lord of Lineage")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE})
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class LordofLineage extends AlternateCard
{
	public static final class LordofLineageAbility1 extends StaticAbility
	{
		public LordofLineageAbility1(GameState state)
		{
			super(state, "Other Vampire creatures you control get +2/+2.");

			SetGenerator otherVampires = RelativeComplement.instance(HasSubType.instance(SubType.VAMPIRE), This.instance());
			SetGenerator otherVampireCreatures = Intersect.instance(otherVampires, CreaturePermanents.instance());
			this.addEffectPart(modifyPowerAndToughness(otherVampireCreatures, +2, +2));
		}
	}

	public static final class LordofLineageAbility2 extends ActivatedAbility
	{
		public LordofLineageAbility2(GameState state)
		{
			super(state, "(T): Put a 2/2 black Vampire creature token with flying onto the battlefield.");
			this.costsTap = true;

			CreateTokensFactory token = new CreateTokensFactory(1, 2, 2, "Put a 2/2 black Vampire creature token with flying onto the battlefield.");
			token.setColors(Color.BLACK);
			token.setSubTypes(SubType.VAMPIRE);
			token.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			this.addEffect(token.getEventFactory());
		}
	}

	public LordofLineage(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		this.setColorIndicator(Color.BLACK);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Other Vampire creatures you control get +2/+2.
		this.addAbility(new LordofLineageAbility1(state));

		// (T): Put a 2/2 black Vampire creature token with flying onto the
		// battlefield.
		this.addAbility(new LordofLineageAbility2(state));
	}
}
