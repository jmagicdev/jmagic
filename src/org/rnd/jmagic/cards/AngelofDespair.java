package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Angel of Despair")
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("3WWBB")
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.RARE), @Printings.Printed(ex = Expansion.GUILDPACT, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class AngelofDespair extends Card
{
	public static final class Despair extends EventTriggeredAbility
	{
		public Despair(GameState state)
		{
			super(state, "When Angel of Despair enters the battlefield, destroy target permanent.");
			this.addPattern(whenThisEntersTheBattlefield());
			Target t = this.addTarget(Permanents.instance(), "target permanent");
			this.addEffect(destroy(targetedBy(t), "Destroy target permanent."));
		}
	}

	public AngelofDespair(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Angel of Despair enters the battlefield, destroy target
		// permanent.
		this.addAbility(new Despair(state));
	}
}
