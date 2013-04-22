package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

import static org.rnd.jmagic.Convenience.*;

@Name("Anathemancer")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE, SubType.WIZARD})
@ManaCost("1BR")
@Printings({@Printings.Printed(ex = Expansion.ALARA_REBORN, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK, Color.RED})
public final class Anathemancer extends Card
{
	public static final class NonBasicHate extends EventTriggeredAbility
	{
		public NonBasicHate(GameState state)
		{
			super(state, "When Anathemancer enters the battlefield, it deals damage to target player equal to the number of nonbasic lands that player controls.");

			this.addPattern(whenThisEntersTheBattlefield());

			Target target = this.addTarget(Players.instance(), "target player");

			SetGenerator amount = Count.instance(Intersect.instance(RelativeComplement.instance(LandPermanents.instance(), HasSuperType.instance(SuperType.BASIC)), ControlledBy.instance(targetedBy(target))));
			this.addEffect(permanentDealDamage(amount, targetedBy(target), "It deals damage to target player equal to the number of nonbasic lands that player controls."));
		}
	}

	public Anathemancer(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new NonBasicHate(state));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Unearth(state, "(5)(B)(R)"));
	}
}
