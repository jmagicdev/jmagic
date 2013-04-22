package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Halo Hunter")
@Types({Type.CREATURE})
@SubTypes({SubType.DEMON})
@ManaCost("2BBB")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class HaloHunter extends Card
{
	public static final class MasterChief extends EventTriggeredAbility
	{
		public MasterChief(GameState state)
		{
			super(state, "When Halo Hunter enters the battlefield, destroy target Angel.");
			this.addPattern(whenThisEntersTheBattlefield());
			Target target = this.addTarget(Intersect.instance(HasSubType.instance(SubType.ANGEL), Permanents.instance()), "target Angel");
			this.addEffect(destroy(targetedBy(target), "Destroy target Angel."));
		}
	}

	public HaloHunter(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Intimidate(state));

		this.addAbility(new MasterChief(state));
	}
}
