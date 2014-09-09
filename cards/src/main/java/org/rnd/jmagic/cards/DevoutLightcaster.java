package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Devout Lightcaster")
@Types({Type.CREATURE})
@SubTypes({SubType.KOR, SubType.CLERIC})
@ManaCost("WWW")
@ColorIdentity({Color.WHITE})
public final class DevoutLightcaster extends Card
{
	public static final class Racism extends EventTriggeredAbility
	{
		public Racism(GameState state)
		{
			super(state, "When Devout Lightcaster enters the battlefield, exile target black permanent.");
			this.addPattern(whenThisEntersTheBattlefield());
			Target target = this.addTarget(Intersect.instance(HasColor.instance(Color.BLACK), Permanents.instance()), "target black permanent");
			this.addEffect(exile(targetedBy(target), "Exile target black permanent."));
		}
	}

	public DevoutLightcaster(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Protection from black
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Protection.FromBlack(state));

		// When Devout Lightcaster enters the battlefield, exile target black
		// permanent.
		this.addAbility(new Racism(state));
	}
}
