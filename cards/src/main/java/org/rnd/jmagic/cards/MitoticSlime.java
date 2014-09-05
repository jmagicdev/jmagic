package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Mitotic Slime")
@Types({Type.CREATURE})
@SubTypes({SubType.OOZE})
@ManaCost("4G")
@Printings({@Printings.Printed(ex = Magic2011.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class MitoticSlime extends Card
{
	public static final class SecondGenerationOozeAbility extends EventTriggeredAbility
	{
		public SecondGenerationOozeAbility(GameState state)
		{
			super(state, "When this creature dies, put two 1/1 green Ooze creature tokens onto the battlefield.");
			this.addPattern(whenThisDies());

			CreateTokensFactory tokens = new CreateTokensFactory(2, 1, 1, "Put two 1/1 green Ooze creature tokens onto the battlefield.");
			tokens.setColors(Color.GREEN);
			tokens.setSubTypes(SubType.OOZE);
			this.addEffect(tokens.getEventFactory());
		}
	}

	public static final class FirstGenerationOozeAbility extends EventTriggeredAbility
	{
		public FirstGenerationOozeAbility(GameState state)
		{
			super(state, "When Mitotic Slime dies, put two 2/2 green Ooze creature tokens onto the battlefield. They have \"When this creature is put into a graveyard, put two 1/1 green Ooze creature tokens onto the battlefield.\"");
			this.addPattern(whenThisDies());

			CreateTokensFactory tokens = new CreateTokensFactory(2, 2, 2, "Put two 2/2 green Ooze creature tokens onto the battlefield. They have \"When this creature is put into a graveyard, put two 1/1 green Ooze creature tokens onto the battlefield.\"");
			tokens.setColors(Color.GREEN);
			tokens.setSubTypes(SubType.OOZE);
			tokens.addAbility(SecondGenerationOozeAbility.class);
			this.addEffect(tokens.getEventFactory());
		}
	}

	public MitoticSlime(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// When Mitotic Slime dies, put
		// two 2/2 green Ooze creature tokens onto the battlefield. They have
		// "When this creature is put into a graveyard, put two 1/1 green Ooze
		// creature tokens onto the battlefield."
		this.addAbility(new FirstGenerationOozeAbility(state));
	}
}
