package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Evil Twin")
@Types({Type.CREATURE})
@SubTypes({SubType.SHAPESHIFTER})
@ManaCost("2UB")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class EvilTwin extends Card
{
	public static final class EvilTwinAbility1 extends ActivatedAbility
	{
		public EvilTwinAbility1(GameState state)
		{
			super(state, "(U)(B), (T): Destroy target creature with the same name as this creature.");
			this.costsTap = true;
			this.setManaCost(new ManaPool("(U)(B)"));

			SetGenerator creaturesWithTheSameName = Intersect.instance(CreaturePermanents.instance(), HasName.instance(NameOf.instance(ABILITY_SOURCE_OF_THIS)));
			SetGenerator target = targetedBy(this.addTarget(creaturesWithTheSameName, "target creature with the same name as this creature"));
			this.addEffect(destroy(target, "Destroy target creature with the same name as this creature."));
		}
	}

	public EvilTwin(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		// You may have Evil Twin enter the battlefield as a copy of any
		// creature on the battlefield except it gains
		// "(U)(B), (T): Destroy target creature with the same name as this creature."
		this.addAbility(new org.rnd.jmagic.abilities.YouMayHaveThisEnterTheBattlefieldAsACopy(CreaturePermanents.instance()).setName("You may have Evil Twin enter the battlefield as a copy of any creature on the battlefield except it gains \"(U)(B), (T): Destroy target creature with the same name as this creature.\"").setAbility(EvilTwinAbility1.class).getStaticAbility(state));
	}
}
