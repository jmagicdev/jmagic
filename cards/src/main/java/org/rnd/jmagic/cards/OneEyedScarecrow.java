package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("One-Eyed Scarecrow")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.SCARECROW})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({})
public final class OneEyedScarecrow extends Card
{
	public static final class OneEyedScarecrowAbility1 extends StaticAbility
	{
		public OneEyedScarecrowAbility1(GameState state)
		{
			super(state, "Creatures with flying your opponents control get -1/-0.");

			this.addEffectPart(modifyPowerAndToughness(Intersect.instance(CreaturePermanents.instance(), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class), ControlledBy.instance(OpponentsOf.instance(You.instance()))), -1, -0));
		}
	}

	public OneEyedScarecrow(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Defender
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));

		// Creatures with flying your opponents control get -1/-0.
		this.addAbility(new OneEyedScarecrowAbility1(state));
	}
}
