package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Genesis Chamber")
@Types({Type.ARTIFACT})
@ManaCost("2")
@ColorIdentity({})
public final class GenesisChamber extends Card
{
	public static final class GenesisChamberAbility0 extends EventTriggeredAbility
	{
		public GenesisChamberAbility0(GameState state)
		{
			super(state, "Whenever a nontoken creature enters the battlefield, if Genesis Chamber is untapped, that creature's controller puts a 1/1 colorless Myr artifact creature token onto the battlefield.");

			SetGenerator nontokens = RelativeComplement.instance(CreaturePermanents.instance(), Tokens.instance());
			SimpleZoneChangePattern pattern = new SimpleZoneChangePattern(null, Battlefield.instance(), nontokens, false);
			this.addPattern(pattern);
			SetGenerator thatCreature = NewObjectOf.instance(TriggerZoneChange.instance(This.instance()));

			this.interveningIf = Intersect.instance(ABILITY_SOURCE_OF_THIS, Untapped.instance());

			CreateTokensFactory f = new CreateTokensFactory(1, 1, 1, "That creature's controller puts a 1/1 colorless Myr artifact creature token onto the battlefield.");
			f.setController(ControllerOf.instance(thatCreature));
			f.setSubTypes(SubType.MYR);
			f.setArtifact();
			this.addEffect(f.getEventFactory());
		}
	}

	public GenesisChamber(GameState state)
	{
		super(state);

		// Whenever a nontoken creature enters the battlefield, if Genesis
		// Chamber is untapped, that creature's controller puts a 1/1 colorless
		// Myr artifact creature token onto the battlefield.
		this.addAbility(new GenesisChamberAbility0(state));
	}
}
