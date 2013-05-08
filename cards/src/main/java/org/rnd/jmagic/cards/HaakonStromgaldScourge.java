package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Haakon, Stromgald Scourge")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE, SubType.KNIGHT})
@ManaCost("1BB")
@Printings({@Printings.Printed(ex = Expansion.COLDSNAP, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class HaakonStromgaldScourge extends Card
{
	public static final class HaakonStromgaldScourgeAbility0 extends StaticAbility
	{
		public HaakonStromgaldScourgeAbility0(GameState state)
		{
			super(state, "You may cast Haakon, Stromgald Scourge from your graveyard, but not from anywhere else.");

			SetGenerator inGraveyard = InZone.instance(GraveyardOf.instance(You.instance()));

			ContinuousEffect.Part playEffect = new ContinuousEffect.Part(ContinuousEffectType.MAY_CAST_LOCATION);
			playEffect.parameters.put(ContinuousEffectType.Parameter.OBJECT, Intersect.instance(This.instance(), inGraveyard));
			playEffect.parameters.put(ContinuousEffectType.Parameter.PERMISSION, Identity.instance(new PlayPermission(You.instance())));
			this.addEffectPart(playEffect);

			org.rnd.jmagic.engine.patterns.SimpleEventPattern castSpell = new org.rnd.jmagic.engine.patterns.SimpleEventPattern(EventType.CAST_SPELL_OR_ACTIVATE_ABILITY);
			castSpell.put(EventType.Parameter.PLAYER, You.instance());
			castSpell.put(EventType.Parameter.OBJECT, RelativeComplement.instance(This.instance(), inGraveyard));

			ContinuousEffect.Part prohibitEffect = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			prohibitEffect.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(castSpell));
			this.addEffectPart(prohibitEffect);

			this.canApply = NonEmpty.instance();
		}
	}

	public static final class HaakonStromgaldScourgeAbility1 extends StaticAbility
	{
		public HaakonStromgaldScourgeAbility1(GameState state)
		{
			super(state, "As long as Haakon is on the battlefield, you may play Knight cards from your graveyard.");

			SetGenerator knights = HasSubType.instance(SubType.KNIGHT);
			SetGenerator inYourGraveyard = InZone.instance(GraveyardOf.instance(You.instance()));

			ContinuousEffect.Part playEffect = new ContinuousEffect.Part(ContinuousEffectType.MAY_PLAY_LOCATION);
			playEffect.parameters.put(ContinuousEffectType.Parameter.OBJECT, Intersect.instance(knights, inYourGraveyard));
			playEffect.parameters.put(ContinuousEffectType.Parameter.PERMISSION, Identity.instance(new PlayPermission(You.instance())));
			this.addEffectPart(playEffect);
		}
	}

	public static final class HaakonStromgaldScourgeAbility2 extends EventTriggeredAbility
	{
		public HaakonStromgaldScourgeAbility2(GameState state)
		{
			super(state, "When Haakon dies, you lose 2 life.");
			this.addPattern(whenThisDies());
			this.addEffect(loseLife(You.instance(), 2, "You lose 2 life."));
		}
	}

	public HaakonStromgaldScourge(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// You may cast Haakon, Stromgald Scourge from your graveyard, but not
		// from anywhere else.
		this.addAbility(new HaakonStromgaldScourgeAbility0(state));

		// As long as Haakon is on the battlefield, you may play Knight cards
		// from your graveyard.
		this.addAbility(new HaakonStromgaldScourgeAbility1(state));

		// When Haakon is put into a graveyard from the battlefield, you lose 2
		// life.
		this.addAbility(new HaakonStromgaldScourgeAbility2(state));
	}
}
