package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Gaddock Teeg")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.ADVISOR, SubType.KITHKIN})
@ManaCost("GW")
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class GaddockTeeg extends Card
{
	public static final class ProhibitWrath extends StaticAbility
	{
		public ProhibitWrath(GameState state)
		{
			super(state, "Noncreature spells with converted mana cost 4 or greater can't be cast.");

			PlayProhibition castSomethingWrathy = new PlayProhibition(Players.instance(), //
			c -> !c.types.contains(Type.CREATURE) && c.manaCost.converted() >= 4);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(castSomethingWrathy));
			this.addEffectPart(part);
		}
	}

	public static final class ProhibitFireball extends StaticAbility
	{
		public ProhibitFireball(GameState state)
		{
			super(state, "Noncreature spells with (X) in their mana costs can't be cast.");

			PlayProhibition castSomethingFirebally = new PlayProhibition(Players.instance(), //
			(c -> {
				if(c.types.contains(Type.CREATURE))
					return false;
				if(c.manaCost == null)
					return false;
				for(ManaSymbol m: c.manaCost)
					if(m.isX)
						return true;
				return false;
			}));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(castSomethingFirebally));
			this.addEffectPart(part);
		}
	}

	public GaddockTeeg(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Noncreature spells with converted mana cost 4 or greater can't be
		// cast.
		this.addAbility(new ProhibitWrath(state));

		// Noncreature spells with {X} in their mana costs can't be cast.
		this.addAbility(new ProhibitFireball(state));
	}
}
