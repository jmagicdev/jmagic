package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Utvara Hellkite")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAGON})
@ManaCost("6RR")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.MYTHIC)})
@ColorIdentity({Color.RED})
public final class UtvaraHellkite extends Card
{
	public static final class UtvaraHellkiteAbility1 extends EventTriggeredAbility
	{
		public UtvaraHellkiteAbility1(GameState state)
		{
			super(state, "Whenever a Dragon you control attacks, put a 6/6 red Dragon creature token with flying onto the battlefield.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.DECLARE_ONE_ATTACKER);
			pattern.put(EventType.Parameter.OBJECT, Intersect.instance(HasSubType.instance(SubType.DRAGON), ControlledBy.instance(You.instance())));
			this.addPattern(pattern);

			CreateTokensFactory tokens = new CreateTokensFactory(1, 6, 6, "Put a 6/6 red Dragon creature token with flying onto the battlefield.");
			tokens.setColors(Color.RED);
			tokens.setSubTypes(SubType.DRAGON);
			tokens.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			this.addEffect(tokens.getEventFactory());
		}
	}

	public UtvaraHellkite(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever a Dragon you control attacks, put a 6/6 red Dragon creature
		// token with flying onto the battlefield.
		this.addAbility(new UtvaraHellkiteAbility1(state));
	}
}
