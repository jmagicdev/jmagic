package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Acid Web Spider")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIDER})
@ManaCost("3GG")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class AcidWebSpider extends Card
{
	public static final class AcidWebSpiderAbility1 extends EventTriggeredAbility
	{
		public AcidWebSpiderAbility1(GameState state)
		{
			super(state, "When Acid Web Spider enters the battlefield, you may destroy target Equipment.");
			this.addPattern(whenThisEntersTheBattlefield());
			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(Permanents.instance(), HasSubType.instance(SubType.EQUIPMENT)), "target Equipment"));
			this.addEffect(youMay(destroy(target, "Destroy target Equipment."), "You may destroy target Equipment."));
		}
	}

	public AcidWebSpider(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(5);

		// Reach
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Reach(state));

		// When Acid Web Spider enters the battlefield, you may destroy target
		// Equipment.
		this.addAbility(new AcidWebSpiderAbility1(state));
	}
}
