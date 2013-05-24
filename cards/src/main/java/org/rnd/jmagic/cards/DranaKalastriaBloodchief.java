package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Drana, Kalastria Bloodchief")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE, SubType.SHAMAN})
@ManaCost("3BB")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class DranaKalastriaBloodchief extends Card
{
	public static final class DranaKalastriaBloodchiefAbility1 extends ActivatedAbility
	{
		public DranaKalastriaBloodchiefAbility1(GameState state)
		{
			super(state, "(X)(B)(B): Target creature gets -0/-X until end of turn and Drana, Kalastria Bloodchief gets +X/+0 until end of turn.");

			this.setManaCost(new ManaPool("(X)(B)(B)"));

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			this.addEffect(createFloatingEffect("Target creature gets -0/-X until end of turn.", modifyPowerAndToughness(targetedBy(target), numberGenerator(0), Subtract.instance(numberGenerator(0), ValueOfX.instance(This.instance())))));

			this.addEffect(createFloatingEffect("Kalastria Bloodchief gets +X/+0 until end of turn.", modifyPowerAndToughness(ABILITY_SOURCE_OF_THIS, ValueOfX.instance(This.instance()), numberGenerator(0))));
		}
	}

	public DranaKalastriaBloodchief(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// (X)(B)(B): Target creature gets -0/-X until end of turn and Drana,
		// Kalastria Bloodchief gets +X/+0 until end of turn.
		this.addAbility(new DranaKalastriaBloodchiefAbility1(state));
	}
}
