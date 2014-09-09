package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Inkmoth Nexus")
@Types({Type.LAND})
@ColorIdentity({})
public final class InkmothNexus extends Card
{
	public static final class InkmothNexusAbility1 extends ActivatedAbility
	{
		public InkmothNexusAbility1(GameState state)
		{
			super(state, "(1): Inkmoth Nexus becomes a 1/1 Blinkmoth artifact creature with flying and infect until end of turn. It's still a land. ");
			this.setManaCost(new ManaPool("(1)"));

			Animator animator = new Animator(ABILITY_SOURCE_OF_THIS, 1, 1);
			animator.addSubType(SubType.BLINKMOTH);
			animator.addType(Type.ARTIFACT);
			animator.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			animator.addAbility(org.rnd.jmagic.abilities.keywords.Infect.class);
			this.addEffect(createFloatingEffect("Inkmoth Nexus becomes a 1/1 Blinkmoth artifact creature with flying and infect until end of turn. It's still a land.", animator.getParts()));
		}
	}

	public InkmothNexus(GameState state)
	{
		super(state);

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		// (1): Inkmoth Nexus becomes a 1/1 Blinkmoth artifact creature with
		// flying and infect until end of turn. It's still a land. (It deals
		// damage to creatures in the form of -1/-1 counters and to players in
		// the form of poison counters.)
		this.addAbility(new InkmothNexusAbility1(state));
	}
}
