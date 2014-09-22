package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import static org.rnd.jmagic.Convenience.*;

@Name("Naya Sojourners")
@Types({Type.CREATURE})
@SubTypes({SubType.ELF, SubType.SHAMAN})
@ManaCost("2RGW")
@ColorIdentity({Color.WHITE, Color.RED, Color.GREEN})
public final class NayaSojourners extends Card
{
	public static final class NayaSojournerTrigger extends org.rnd.jmagic.abilityTemplates.SojournerTrigger
	{
		public NayaSojournerTrigger(GameState state)
		{
			super(state, "When you cycle Naya Sojourners or it's put into a graveyard from the battlefield, you may put a +1/+1 counter on target creature.");

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			this.addEffect(youMay(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, targetedBy(target), "Put a +1/+1 counter on target creature."), "You may put a +1/+1 counter on target creature."));
		}
	}

	public NayaSojourners(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(3);

		this.addAbility(new NayaSojournerTrigger(state));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Cycling(state, "(2)(G)"));
	}
}
