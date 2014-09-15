package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Vanguard of Brimaz")
@Types({Type.CREATURE})
@SubTypes({SubType.CAT, SubType.SOLDIER})
@ManaCost("WW")
@ColorIdentity({Color.WHITE})
public final class VanguardofBrimaz extends Card
{
	public static final class VanguardofBrimazAbility1 extends EventTriggeredAbility
	{
		public VanguardofBrimazAbility1(GameState state)
		{
			super(state, "Whenever you cast a spell that targets Vanguard of Brimaz, put a 1/1 white Cat Soldier creature token with vigilance onto the battlefield.");
			this.addPattern(heroic());

			CreateTokensFactory cat = new CreateTokensFactory(1, 1, 1, "Put a 1/1 white Cat Soldier creature token with vigilance onto the battlefield.");
			cat.setColors(Color.WHITE);
			cat.setSubTypes(SubType.CAT);
			cat.setSubTypes(SubType.SOLDIER);
			cat.addAbility(org.rnd.jmagic.abilities.keywords.Vigilance.class);
			this.addEffect(cat.getEventFactory());
		}
	}

	public VanguardofBrimaz(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Vigilance
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));

		// Heroic \u2014 Whenever you cast a spell that targets Vanguard of
		// Brimaz, put a 1/1 white Cat Soldier creature token with vigilance
		// onto the battlefield.
		this.addAbility(new VanguardofBrimazAbility1(state));
	}
}
