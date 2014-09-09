package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Sylvan Basilisk")
@Types({Type.CREATURE})
@SubTypes({SubType.BASILISK})
@ManaCost("3GG")
@ColorIdentity({Color.GREEN})
public final class SylvanBasilisk extends Card
{
	public static final class Deathglare extends EventTriggeredAbility
	{
		public Deathglare(GameState state)
		{
			super(state, "Whenever Sylvan Basilisk becomes blocked by a creature, destroy that creature.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_BLOCKED_BY_ONE);
			pattern.put(EventType.Parameter.ATTACKER, ABILITY_SOURCE_OF_THIS);
			pattern.put(EventType.Parameter.DEFENDER, CreaturePermanents.instance());
			this.addPattern(pattern);

			SetGenerator thatCreature = EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.DEFENDER);
			this.addEffect(destroy(thatCreature, "Destroy that creature"));
		}
	}

	public SylvanBasilisk(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(4);

		this.addAbility(new Deathglare(state));
	}
}
