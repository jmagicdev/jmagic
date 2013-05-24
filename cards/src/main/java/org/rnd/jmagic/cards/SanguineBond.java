package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Sanguine Bond")
@Types({Type.ENCHANTMENT})
@ManaCost("3BB")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class SanguineBond extends Card
{
	public static final class Vampiricism extends EventTriggeredAbility
	{
		public Vampiricism(GameState state)
		{
			super(state, "Whenever you gain life, target opponent loses that much life.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.GAIN_LIFE);
			pattern.put(EventType.Parameter.PLAYER, You.instance());
			this.addPattern(pattern);

			Target target = this.addTarget(OpponentsOf.instance(You.instance()), "target opponent");

			this.addEffect(loseLife(targetedBy(target), EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.NUMBER), "Target opponent loses that much life."));
		}
	}

	public SanguineBond(GameState state)
	{
		super(state);

		this.addAbility(new Vampiricism(state));
	}
}
