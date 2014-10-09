package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Pharika, God of Affliction")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.GOD})
@ManaCost("1BG")
@ColorIdentity({Color.BLACK, Color.GREEN})
public final class PharikaGodofAffliction extends Card
{
	public static final class PharikaGodofAfflictionAbility1 extends StaticAbility
	{
		public PharikaGodofAfflictionAbility1(GameState state)
		{
			super(state, "As long as your devotion to black and green is less than seven, Pharika isn't a creature.");

			SetGenerator notEnoughDevotion = Intersect.instance(Between.instance(null, 6), DevotionTo.instance(Color.BLACK, Color.GREEN));
			this.canApply = Both.instance(this.canApply, notEnoughDevotion);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.REMOVE_TYPES);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			part.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(Type.CREATURE));
			this.addEffectPart(part);
		}
	}

	public static final class PharikaGodofAfflictionAbility2 extends ActivatedAbility
	{
		public PharikaGodofAfflictionAbility2(GameState state)
		{
			super(state, "(B)(G): Exile target creature card from a graveyard. Its owner puts a 1/1 black and green Snake enchantment creature token with deathtouch onto the battlefield.");
			this.setManaCost(new ManaPool("(B)(G)"));

			SetGenerator deadThings = Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(Players.instance())));
			SetGenerator target = targetedBy(this.addTarget(deadThings, "target creature card from a graveyard"));

			this.addEffect(exile(target, "Exile target creature card from a graveyard."));

			SetGenerator itsOwner = OwnerOf.instance(target);

			CreateTokensFactory snake = new CreateTokensFactory(1, 1, 1, "Its owner puts a 1/1 black and green Snake enchantment creature token with deathtouch onto the battlefield.");
			snake.setController(itsOwner);
			snake.setColors(Color.BLACK, Color.GREEN);
			snake.setSubTypes(SubType.SNAKE);
			snake.setEnchantment();
			snake.addAbility(org.rnd.jmagic.abilities.keywords.Deathtouch.class);
			this.addEffect(snake.getEventFactory());
		}
	}

	public PharikaGodofAffliction(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Indestructible
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Indestructible(state));

		// As long as your devotion to black and green is less than seven,
		// Pharika isn't a creature.
		this.addAbility(new PharikaGodofAfflictionAbility1(state));

		// (B)(G): Exile target creature card from a graveyard. Its owner puts a
		// 1/1 black and green Snake enchantment creature token with deathtouch
		// onto the battlefield.
		this.addAbility(new PharikaGodofAfflictionAbility2(state));
	}
}
